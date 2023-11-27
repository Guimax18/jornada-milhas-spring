export interface Promocao {
    id: number
    destino: string
    imagem: string
    preco: number
}

export interface Estado {
    id: number;
    nome: string;
    sigla: string;
}

export interface Depoimento {
    id: number;
    texto: string;
    autor: string;
    avatar: string;
}

export interface PessoaUsuaria {
  nome: string;
  nascimento: string;
  cpf: string;
  telefone: string;
  email: string;
  senha: string;
  cidade: string;
  estado: Estado;
  genero: string;
}

export interface Resultado {
    resultado: Passagem[];
    precoMin: any;
    precoMax: any;
    passagem: Passagem;
    dataIda: Date;
    dataVolta: Date;
    orcamento: Array<Orcamento>;
    total: number
}

export interface Passagem{
    tipo: string;
    precoIda: number;
    precoVolta: number;
    taxaEmbarque: number;
    conexoes: number;
    tempoVoo: number;
    origem: Estado;
    destino: Estado;
    companhia: Companhia;
    dataIda: Date;
    dataVolta: Date;
    total: number;
    orcamento: Array<Orcamento>;
}

export interface Companhia{
    id: string;
    nome: string;
}

export interface Orcamento{
    descricao: string;
    preco: number;
    taxaEmbarque: number;
    total: number
}

export interface MinMaxPrice {
    min: number;
    max: number;
}

export interface DadosBusca{
    somenteIda?: boolean;
    passageirosAdultos?: number;
    passageirosCriancas?: number;
    passageirosBebes?: number;
    tipo?: string;
    turno?: string;
    origemId?: number;
    destinoId?: number;
    minMaxPrice: MinMaxPrice
    conexoes?: number;
    tempoVoo?: number;
    dataIda: string;
    dataVolta?: string;
    companhiasId?: number[];
    pagina: number;
    porPagina: number;
}