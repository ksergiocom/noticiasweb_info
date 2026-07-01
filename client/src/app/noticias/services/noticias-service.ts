import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Noticia } from '../interfaces/Noticia';
import { Observable } from 'rxjs';
import { PageDTO } from '@/shared/interfaces/PageDTO';

@Injectable({
  providedIn: 'root',
})
export class NoticiasService {
  private http = inject(HttpClient);

  private readonly apiUrl = '/api/noticias';

  public listar(page = 0, size = 10): Observable<PageDTO<Noticia>> {
    return this.http.get<PageDTO<Noticia>>(this.apiUrl, {
      params: {
        page,
        size,
      },
    });
  }

  public listarPorPeriodicos(
    page = 0,
    size = 10,
    periodicosIds: number[] | null = null,
    texto: string | null = null,
  ): Observable<PageDTO<Noticia>> {
    return this.http.post<PageDTO<Noticia>>(`${this.apiUrl}/buscar`, {
      page,
      size,
      periodicoIds: periodicosIds,
      texto,
    });
  }

  public obtener(id: number): Observable<Noticia> {
    return this.http.get<Noticia>(`${this.apiUrl}/${id}`);
  }
}
