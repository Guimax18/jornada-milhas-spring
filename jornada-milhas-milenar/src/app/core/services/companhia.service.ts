import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import { Companhia } from "../types/type";
import { TokenService } from "./token.service";

@Injectable({
  providedIn: 'root'
})
export class CompanhiaService {

  private apiUrl: string = environment.apiUrl;

  constructor(
    private httpClient: HttpClient,
    private token: TokenService
  ) { }

  listar () : Observable<Companhia[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.token.retornarToken()}`);
    return this.httpClient.get<Companhia[]>(`${this.apiUrl}/companhias/todos`, { headers })
  }
}
