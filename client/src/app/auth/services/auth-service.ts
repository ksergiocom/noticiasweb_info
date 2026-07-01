import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { JWTDTO, LoginDTO } from '../interfaces/JWTDTO';
import { tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  public http = inject(HttpClient);

  private readonly API_URL = '/api/auth';
  private readonly TOKEN_KEY = 'jwt_token';

  public isLoggedIn = signal<boolean>(!!this.getToken());

  public login(dto: LoginDTO) {
    return this.http.post<JWTDTO>(`${this.API_URL}/login`, dto).pipe(
      tap((res) => {
        console.log({res})
        localStorage.setItem(this.TOKEN_KEY, res.token);
        this.isLoggedIn.set(true);
      })
    );
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
    this.isLoggedIn.set(false);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }
}
