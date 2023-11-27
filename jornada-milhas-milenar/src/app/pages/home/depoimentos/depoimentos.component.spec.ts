import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { DepoimentosComponent } from './depoimentos.component';
import { DepoimentoService } from 'src/app/core/services/depoimento.service';
import { Depoimento } from 'src/app/core/types/type';

describe('DepoimentosComponent', () => {
  let component: DepoimentosComponent;
  let depoimentoService: DepoimentoService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DepoimentosComponent ],
      providers: [ DepoimentoService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    depoimentoService = TestBed.inject(DepoimentoService);
    component = new DepoimentosComponent(depoimentoService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call service.listar() on ngOnInit', () => {
    spyOn(depoimentoService, 'listar').and.returnValue(of([]));
    component.ngOnInit();
    expect(depoimentoService.listar).toHaveBeenCalled();
  });
});
