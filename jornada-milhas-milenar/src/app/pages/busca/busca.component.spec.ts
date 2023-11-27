import { TestBed } from '@angular/core/testing';
import { take } from 'rxjs/operators';
import { FormBuscaService } from 'src/app/core/services/form-busca.service';
import { PassagensService } from 'src/app/core/services/passagens.service';
import { DadosBusca, Passagem, Resultado } from 'src/app/core/types/type';
import { BuscaComponent } from './busca.component';

describe('BuscaComponent', () => {
  let component: BuscaComponent;
  let passagensService: PassagensService;
  let formBuscaService: FormBuscaService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BuscaComponent ],
      providers: [ PassagensService, FormBuscaService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    passagensService = TestBed.inject(PassagensService);
    formBuscaService = TestBed.inject(FormBuscaService);
    component = new BuscaComponent(passagensService, formBuscaService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get passagens', () => {
    const buscaPadrao : DadosBusca = {
      dataIda: new Date().toISOString(),
      pagina: 1,
      porPagina: 25,
      somenteIda: false,
      passageirosAdultos: 1,
      tipo: "Executiva"
    }
    spyOn(formBuscaService, 'formEstaValido').and.returnValue(true);
    spyOn(formBuscaService, 'obterDadosBusca').and.returnValue(buscaPadrao);
    spyOn(passagensService, 'getPassagens').and.callFake(() => {
      return {
        pipe: () => {
          return {
            subscribe: (callback: any) => {
              callback({
                resultado: [{id: 1, preco: 100, origem: 'São Paulo', destino: 'Rio de Janeiro'}],
                precoMin: 50,
                precoMax: 200
              });
            }
          }
        }
      }
    });
    component.ngOnInit();
    expect(component.passagens.length).toBe(1);
    expect(component.passagens[0].id).toBe(1);
    expect(component.passagens[0].preco).toBe(100);
    expect(component.passagens[0].origem).toBe('São Paulo');
    expect(component.passagens[0].destino).toBe('Rio de Janeiro');
  });
});
