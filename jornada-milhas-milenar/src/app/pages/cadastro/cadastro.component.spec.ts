import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';
import { CadastroComponent } from './cadastro.component';
import { CadastroService } from 'src/app/core/services/cadastro.service';
import { FormularioService } from 'src/app/core/services/formulario.service';
import { PessoaUsuaria } from 'src/app/core/types/type';
import { of } from 'rxjs';

describe('CadastroComponent', () => {
  let component: CadastroComponent;
  let fixture: ComponentFixture<CadastroComponent>;
  let formularioServiceSpy: jasmine.SpyObj<FormularioService>;
  let cadastroServiceSpy: jasmine.SpyObj<CadastroService>;
  let routerSpy: jasmine.SpyObj<Router>;

  beforeEach(() => {
    formularioServiceSpy = jasmine.createSpyObj('FormularioService', ['getCadastro']);
    cadastroServiceSpy = jasmine.createSpyObj('CadastroService', ['cadastrar']);
    routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      declarations: [CadastroComponent],
      providers: [
        { provide: FormularioService, useValue: formularioServiceSpy },
        { provide: CadastroService, useValue: cadastroServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    });

    fixture = TestBed.createComponent(CadastroComponent);
    component = fixture.componentInstance;
  });

  it('deve criar o componente', () => {
    expect(component).toBeTruthy();
  });

  it('deve cadastrar uma nova pessoa usuária', () => {
    const formCadastroSpy = jasmine.createSpyObj('FormGroup', ['getRawValue', 'valid']);
    const novoCadastro: PessoaUsuaria = { nome: 'João', email: 'joao@example.com', senha: 'senha123' };

    formularioServiceSpy.getCadastro.and.returnValue(formCadastroSpy);
    formCadastroSpy.getRawValue.and.returnValue(novoCadastro);
    formCadastroSpy.valid = true;
    cadastroServiceSpy.cadastrar.and.returnValue(of({}));
    routerSpy.navigate.and.returnValue(Promise.resolve(true));

    component.cadastrar();

    expect(formularioServiceSpy.getCadastro).toHaveBeenCalled();
    expect(formCadastroSpy.getRawValue).toHaveBeenCalled();
    expect(cadastroServiceSpy.cadastrar).toHaveBeenCalledWith(novoCadastro);
    expect(routerSpy.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('não deve cadastrar uma nova pessoa usuária se o formulário for inválido', () => {
    const formCadastroSpy = jasmine.createSpyObj('FormGroup', ['getRawValue', 'valid']);

    formularioServiceSpy.getCadastro.and.returnValue(formCadastroSpy);
    formCadastroSpy.valid = false;

    component.cadastrar();

    expect(formularioServiceSpy.getCadastro).toHaveBeenCalled();
    expect(formCadastroSpy.getRawValue).not.toHaveBeenCalled();
    expect(cadastroServiceSpy.cadastrar).not.toHaveBeenCalled();
    expect(routerSpy.navigate).not.toHaveBeenCalled();
  });
});

