import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Promocao } from '../types/type';
import { environment } from 'src/environments/environment';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class PromocaoService {

  private apiUrl: string = environment.apiUrl;

  constructor(
    private httpClient: HttpClient,
    private token: TokenService
  ) { }

  listar () : Observable<Promocao[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token.retornarToken()}`);
    return this.httpClient.get<Promocao[]>(`${this.apiUrl}/promocoes/todos`, { headers })
  }
}
