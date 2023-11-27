import { TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { AutenticacaoService } from 'src/app/core/services/autenticacao.service';
import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let authService: AutenticacaoService;
  let router: Router;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      providers: [ AutenticacaoService, Router, FormBuilder ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    authService = TestBed.inject(AutenticacaoService);
    router = TestBed.inject(Router);
    formBuilder = TestBed.inject(FormBuilder);
    component = new LoginComponent(formBuilder, authService, router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call authService.autenticar() on login', () => {
    spyOn(authService, 'autenticar').and.returnValue(of({}));
    spyOn(router, 'navigateByUrl');
    component.loginForm.setValue({email: 'test@test.com', senha: '123456'});
    component.login();
    expect(authService.autenticar).toHaveBeenCalledWith('test@test.com', '123456');
    expect(router.navigateByUrl).toHaveBeenCalledWith('/');
    expect(component.loginForm.value.email).toBeNull();
    expect(component.loginForm.value.senha).toBeNull();
  });
});
