import { TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { HomeComponent } from './home.component';
import { PromocaoService } from 'src/app/core/services/promocao.service';

describe('HomeComponent', () => {
  let component: HomeComponent;
  let promocaoService: PromocaoService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeComponent ],
      providers: [ PromocaoService, Router ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    promocaoService = TestBed.inject(PromocaoService);
    router = TestBed.inject(Router);
    component = new HomeComponent(promocaoService, router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call servicoPromocao.listar() on ngOnInit', () => {
    spyOn(promocaoService, 'listar').and.returnValue(of({}));
    component.ngOnInit();
    expect(promocaoService.listar).toHaveBeenCalled();
  });

  it('should navigate to busca on navegarParaBusca', () => {
    spyOn(router, 'navigate');
    component.navegarParaBusca({});
    expect(router.navigate).toHaveBeenCalledWith(['busca']);
  });
});
