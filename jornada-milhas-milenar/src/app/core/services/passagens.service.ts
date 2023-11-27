import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, take } from 'rxjs';
import { environment } from 'src/environments/environment';
import { DadosBusca, Resultado } from '../types/type';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class PassagensService {
  apiUrl: string = environment.apiUrl;
  precoMin: number = 0;
  precoMax: number = 0;
  constructor(
    private httpClient: HttpClient,
    private token: TokenService
  ) { }
  getPassagens(search: DadosBusca): Observable<Resultado> {
    const body = {
      somenteIda: search.somenteIda,
      passageirosAdultos: search.passageirosAdultos,
      passageirosCriancas: search.passageirosCriancas,
      passageirosBebes: search.passageirosCriancas,
      tipo: search.tipo === "Econômica" ? "ECONOMICA" : "EXECUTIVA",
      turno: search.turno,
      origemId: search.origemId,
      companhiasId: search.companhiasId,
      destinoId: search.destinoId,
      minMaxPrice: search.minMaxPrice,
      conexoes: search.conexoes,
      tempoVoo: search.tempoVoo,
      dataIda: search.dataIda,
      dataVolta: search.dataVolta,
      pagina: search.pagina,
      porPagina: search.porPagina
    }
    const headers = new HttpHeaders()
      .append('Authorization', `Bearer ${this.token.retornarToken()}`)
      .append('Content-type', 'application/json');
    const obs = this.httpClient.post<Resultado>(`${this.apiUrl}/passagem/search`, JSON.stringify(body), { headers });
    obs.pipe(take(1)).subscribe(res => {
      this.precoMin = res.orcamento.reduce((min, o) => {
        return o.preco < min ? o.preco: min;
      }, res.orcamento[0].preco);

      this.precoMax = res.orcamento.reduce((max, o) => {
        return o.preco > max ? o.preco: max;
      }, res.orcamento[0].preco);
    }
    )
    return obs
  }

  converterParametroParaString(busca: DadosBusca) {
    const query = Object.entries(busca)
      .map(([key, value]) => {
        if (!value) {
          return ''
        }
        return `${key}=${value}`
      })
      .join('&')
    return query
  }
}
