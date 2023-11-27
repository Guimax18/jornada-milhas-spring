import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, map, startWith } from 'rxjs';
import { EstadoService } from 'src/app/core/services/estado.service';
import { Estado } from 'src/app/core/types/type';

@Component({
  selector: 'app-dropdown-uf',
  templateUrl: './dropdown-uf.component.html',
  styleUrls: ['./dropdown-uf.component.scss']
})
export class DropdownUfComponent implements OnInit {
  @Input() label: string = '';
  @Input() iconePrefixo: string = '';
  @Input() control!: FormControl;
  @Input() placeholder: string = '';

  unidadesFederativas: Estado[] = [];

  filteredOptions$?: Observable<Estado[]>;


  constructor(
    private EstadoService: EstadoService) {

  }

  ngOnInit(): void {
    this.EstadoService.listar()
      .subscribe(dados => {
        this.unidadesFederativas = dados
        console.log(this.unidadesFederativas)
      })
    this.filteredOptions$ = this.control.valueChanges.pipe(
      startWith(''),
      map(value => this.filtrarUfs(value))
    )
  }

  filtrarUfs(value: string | Estado): Estado[] {
    const nomeUf = typeof value === 'string' ? value : value?.nome;
    const valorFiltrado = nomeUf?.toLowerCase();
    const result = this.unidadesFederativas.filter(
      estado => estado.nome.toLowerCase().includes(valorFiltrado)
    )
    return result
  }

  displayFn(estado: Estado): string {
    return estado && estado.nome ? estado.nome : '';
  }
}
