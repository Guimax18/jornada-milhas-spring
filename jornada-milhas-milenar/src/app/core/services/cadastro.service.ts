import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PessoaUsuaria } from '../types/type';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  private apiUrl: string = environment.apiUrl;
  constructor(
    private http: HttpClient,
    private token: TokenService) { }

  cadastrar(pessoaUsuaria: PessoaUsuaria): Observable<PessoaUsuaria> {
    console.log(pessoaUsuaria);
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    const body = {
      nome: pessoaUsuaria.nome,
      nascimento: pessoaUsuaria.nascimento,
      cpf: pessoaUsuaria.cpf,
      telefone: pessoaUsuaria.telefone,
      email: pessoaUsuaria.email,
      senha: pessoaUsuaria.senha,
      genero: pessoaUsuaria.genero,
      cidade: pessoaUsuaria.cidade,
      estado: {
        nome: pessoaUsuaria.estado.nome,
        sigla: pessoaUsuaria.estado.sigla
      }
    };
    return this.http.post<PessoaUsuaria>(`${this.apiUrl}/auth/register`, JSON.stringify(body), { headers });
  }

  buscarCadastro(): Observable<PessoaUsuaria> {
    return this.http.get<PessoaUsuaria>(`${this.apiUrl}/user`);
  }

  editarCadastro(pessoaUsuaria: PessoaUsuaria): Observable<PessoaUsuaria> {
    const headers = new HttpHeaders()
      .append('Content-type', 'application/json')
      .append('Authorization', `Bearer ${this.token.retornarToken()}`);
    const body = {
      nome: pessoaUsuaria.nome,
      nascimento: pessoaUsuaria.nascimento,
      cpf: pessoaUsuaria.cpf,
      telefone: pessoaUsuaria.telefone,
      email: pessoaUsuaria.email,
      senha: pessoaUsuaria.senha,
      genero: pessoaUsuaria.genero,
      cidade: pessoaUsuaria.cidade,
      estado: {
        nome: pessoaUsuaria.estado.nome,
        sigla: pessoaUsuaria.estado.sigla
      }
    };
    return this.http.put<PessoaUsuaria>(`${this.apiUrl}/user`, JSON.stringify(body), { headers });
  }

}
