import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Estado } from '../types/type';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class EstadoService {
  private apiUrl: string = environment.apiUrl
  private cache$?: Observable<Estado[]>;

  constructor(
    private http: HttpClient,
    private token: TokenService
  ) { 
  }

  listar() : Observable<Estado[]> {
    if (!this.cache$) {
      this.cache$ = this.requestEstados().pipe(
        shareReplay(1)
      );
    }

    return this.cache$;
  }

  private requestEstados(): Observable<Estado[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token.retornarToken()}`);
    return this.http.get<Estado[]>(`${this.apiUrl}/estados/todos`, { headers });
  }
}
