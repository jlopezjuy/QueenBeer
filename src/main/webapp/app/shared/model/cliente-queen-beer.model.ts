import { Moment } from 'moment';

export const enum CategoriaCliente {
    BAR = 'BAR',
    DISTRIBUIDOR = ' DISTRIBUIDOR',
    CONSUMIDOR_FINAL = ' CONSUMIDOR_FINAL'
}

export const enum CondicionFiscal {
    RESPONSABLE_INSCRIPTO = 'RESPONSABLE_INSCRIPTO',
    MONOTRIBUTISTA = 'MONOTRIBUTISTA',
    CONSUMIDOR_FINAL = 'CONSUMIDOR_FINAL',
    OTRO = 'OTRO'
}

export const enum Provincia {
    MISIONES = 'MISIONES',
    SAN_LUIS = ' SAN_LUIS',
    SAN_JUAN = ' SAN_JUAN',
    ENTRE_RÍOS = ' ENTRE_RÍOS',
    SANTA_CRUZ = ' SANTA_CRUZ',
    RÍO_NEGRO = ' RÍO_NEGRO',
    CHUBUT = ' CHUBUT',
    CÓRDOBA = ' CÓRDOBA',
    MENDOZA = ' MENDOZA',
    LA_RIOJA = ' LA_RIOJA',
    CATAMARCA = ' CATAMARCA',
    LA_PAMPA = ' LA_PAMPA',
    SANTIAGO_DEL_ESTERO = ' SANTIAGO_DEL_ESTERO',
    CORRIENTES = ' CORRIENTES',
    SANTA_FE = ' SANTA_FE',
    TUCUMÁN = ' TUCUMÁN',
    NEUQUÉN = ' NEUQUÉN',
    SALTA = ' SALTA',
    CHACO = ' CHACO',
    FORMOSA = ' FORMOSA',
    JUJUY = ' JUJUY',
    CIUDAD_AUTÓNOMA_DE_BUENOS_AIRES = ' CIUDAD_AUTÓNOMA_DE_BUENOS_AIRES',
    BUENOS_AIRES = ' BUENOS_AIRES',
    TIERRA_DEL_FUEGO = ' TIERRA_DEL_FUEGO',
    ANTÁRTIDA_E_ISLAS_DEL_ATLÁNTICO_SUR = '  ANTÁRTIDA_E_ISLAS_DEL_ATLÁNTICO_SUR'
}

export interface IClienteQueenBeer {
    id?: number;
    nombreFantasia?: string;
    razonSocial?: string;
    categoria?: CategoriaCliente;
    cuit?: string;
    condicionFiscal?: CondicionFiscal;
    fechaAlta?: Moment;
    telefono?: string;
    direccion?: string;
    localidadCiudad?: string;
    privincia?: Provincia;
    codigoPostal?: number;
    email?: string;
}

export class ClienteQueenBeer implements IClienteQueenBeer {
    constructor(
        public id?: number,
        public nombreFantasia?: string,
        public razonSocial?: string,
        public categoria?: CategoriaCliente,
        public cuit?: string,
        public condicionFiscal?: CondicionFiscal,
        public fechaAlta?: Moment,
        public telefono?: string,
        public direccion?: string,
        public localidadCiudad?: string,
        public privincia?: Provincia,
        public codigoPostal?: number,
        public email?: string
    ) {}
}
