import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Depoimento } from '../types/type';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class DepoimentoService {

  private apiUrl: string = environment.apiUrl

  constructor(
    private http: HttpClient,
    private token: TokenService
  ) { 
  }

  listar() : Observable<Depoimento[]>{
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token.retornarToken()}`);
    return this.http.get<Depoimento[]>(`${this.apiUrl}/depoimentos/todos`, { headers });
  }
}
