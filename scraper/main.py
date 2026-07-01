import feedparser
import requests
from bs4 import BeautifulSoup
import html
from datetime import datetime

BASE_URL = os.environ.get("NOTICIAS_API_URL", "http://localhost:8080/api")
USERNAME = os.environ.get("NOTICIAS_ADMIN_USERNAME")
PASSWORD = os.environ.get("NOTICIAS_ADMIN_PASSWORD")

#################################################
## Conseguir el token JWT
#################################################

res = requests.post(
    f"{BASE_URL}/auth/login",
    json={
        "username" : USERNAME,
        "password" : PASSWORD,
    }
)

if not res.ok:
    print("Error haciendo login!")
    exit(1)

data = res.json()
token = data["token"]

#################################################
## Buscar el RSS por periodico
## e insertar noticias una a una
#################################################

response = requests.get(f"{BASE_URL}/periodicos/")
periodicos = response.json()

for p in periodicos:
    print("Leyendo periódico:", p["nombre"])

    data = feedparser.parse(p["rss"])
    entries = data["entries"]

    for e in entries:
        titulo = e.get("title")
        descripcion = e.get("summary")
        url = e.get("link")
        published_parsed = e.get("published_parsed")

        # Validaciones para que no pete:
        # Fecha de publicación
        if published_parsed:
            fecha_publicacion = datetime(*published_parsed[:6]).isoformat()
        else:
            fecha_publicacion = datetime.now().isoformat()
        # Summary sanitizado
        if not descripcion:
            descripcion = ""
        descripcion = html.unescape(descripcion)
        soup = BeautifulSoup(descripcion, "html.parser")
        texto = soup.get_text(" ", strip=True)
        descripcion = texto

        noticia = {
            "titulo": titulo,
            "url": url,
            "descripcion": descripcion,
            "fechaPublicacion": fecha_publicacion,
            "periodicoId": p["id"]
        }

        res = requests.post(
            f"{BASE_URL}/noticias",
            json=noticia,
            headers={
                "Authorization": f"Bearer {token}",
                "Content-Type": "application/json",
            }
        )

        print("-------------------")
        print("Título:", titulo)
        print("Status:", res.status_code)

        if res.ok:
            print("Creada:", res.json())
        else:
            print("Error:", res.text)