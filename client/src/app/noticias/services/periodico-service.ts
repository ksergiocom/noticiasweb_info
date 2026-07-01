import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  Periodico,
  CrearPeriodicoDTO,
  ActualizarPeriodicoDTO,
} from '@/noticias/interfaces/Periodico';

@Injectable({
  providedIn: 'root',
})
export class PeriodicoService {
  private http = inject(HttpClient);

  private readonly apiUrl = '/api/periodicos';

  public listar(): Observable<Periodico[]> {
    return this.http.get<Periodico[]>(this.apiUrl);
  }

  public obtener(id: number): Observable<Periodico> {
    return this.http.get<Periodico>(`${this.apiUrl}/${id}`);
  }

  public crear(dto: CrearPeriodicoDTO): Observable<Periodico> {
    return this.http.post<Periodico>(this.apiUrl, dto);
  }

  public eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  public actualizar(id: number, dto: ActualizarPeriodicoDTO): Observable<Periodico> {
    return this.http.put<Periodico>(`${this.apiUrl}/${id}`, dto);
  }
}
