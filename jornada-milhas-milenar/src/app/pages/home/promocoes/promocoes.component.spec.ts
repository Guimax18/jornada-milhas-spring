import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { PromocoesComponent } from './promocoes.component';
import { PromocaoService } from 'src/app/core/services/promocao.service';

describe('PromocoesComponent', () => {
  let component: PromocoesComponent;
  let promocaoService: PromocaoService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PromocoesComponent ],
      providers: [ PromocaoService ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    promocaoService = TestBed.inject(PromocaoService);
    component = new PromocoesComponent(promocaoService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call service.listar() on ngOnInit', () => {
    spyOn(promocaoService, 'listar').and.returnValue(of([]));
    component.ngOnInit();
    expect(promocaoService.listar).toHaveBeenCalled();
  });
});
