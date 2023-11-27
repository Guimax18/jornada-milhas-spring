import { TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { CadastroService } from 'src/app/core/services/cadastro.service';
import { FormularioService } from 'src/app/core/services/formulario.service';
import { TokenService } from 'src/app/core/services/token.service';
import { UserService } from 'src/app/core/services/user.service';
import { PessoaUsuaria } from 'src/app/core/types/type';
import { PerfilComponent } from './perfil.component';

describe('PerfilComponent', () => {
  let component: PerfilComponent;
  let cadastroService: CadastroService;
  let tokenService: TokenService;
  let formularioService: FormularioService;
  let userService: UserService;
  let router: Router;
  let formBuilder: FormBuilder;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PerfilComponent ],
      providers: [ CadastroService, TokenService, FormularioService, UserService, Router, FormBuilder ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    cadastroService = TestBed.inject(CadastroService);
    tokenService = TestBed.inject(TokenService);
    formularioService = TestBed.inject(FormularioService);
    userService = TestBed.inject(UserService);
    router = TestBed.inject(Router);
    formBuilder = TestBed.inject(FormBuilder);
    component = new PerfilComponent(cadastroService, tokenService, formularioService, userService, router);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call cadastroService.buscarCadastro() on ngOnInit', () => {
    spyOn(cadastroService, 'buscarCadastro').and.returnValue(of({}));
    component.ngOnInit();
    expect(cadastroService.buscarCadastro).toHaveBeenCalled();
  });

  it('should call formularioService.getCadastro() on carregarFormulario', () => {
    spyOn(formularioService, 'getCadastro').and.returnValue(formBuilder.group({}));
    component.cadastro = {nome: 'test', nascimento: '2022-01-01', cpf: '12345678901', cidade: 'test', email: 'test@test.com',
     senha: '123456', genero: 'M', telefone: '123456789', estado: 'test'};
    component.carregarFormulario();
    expect(formularioService.getCadastro).toHaveBeenCalled();
    expect(component.form?.value.nome).toBe('test');
    expect(component.form?.value.nascimento).toBe('2022-01-01');
    expect(component.form?.value.cpf).toBe('12345678901');
    expect(component.form?.value.cidade).toBe('test');
    expect(component.form?.value.email).toBe('test@test.com');
    expect(component.form?.value.senha).toBe('123456');
    expect(component.form?.value.genero).toBe('M');
    expect(component.form?.value.telefone).toBe('123456789');
    expect(component.form?.value.estado).toBe('test');
  });

  it('should call cadastroService.atualizarCadastro() on atualizar', () => {
    spyOn(cadastroService, 'atualizarCadastro').and.returnValue(of({}));
    component.form = formBuilder.group({nome: 'test', nascimento: '2022-01-01', cpf: '12345678901', 
    cidade: 'test', email: 'test@test.com', senha: '123456', genero: 'M', telefone: '123456789', estado: 'test'});
    component.atualizar();
    expect(cadastroService.atualizarCadastro).toHaveBeenCalledWith({nome: 'test', nascimento: '2022-01-01', 
    cpf: '12345678901', telefone: '123456789'});
  });
});
