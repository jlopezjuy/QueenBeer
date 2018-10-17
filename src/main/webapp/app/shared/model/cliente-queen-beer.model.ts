import { Moment } from 'moment';

export const enum CategoriaCliente {
    BAR = 'BAR',
    DISTRIBUIDOR = 'DISTRIBUIDOR'
}

export const enum CondicionFiscal {
    RESPONSABLE_INSCRIPTO = 'RESPONSABLE_INSCRIPTO',
    MONOTRIBUTISTA = 'MONOTRIBUTISTA',
    CONSUMIDOR_FINAL = 'CONSUMIDOR_FINAL',
    OTRO = 'OTRO'
}

export const enum Provincia {
    BUENOS_AIRES = 'BUENOS_AIRES',
    CATAMARCA = 'CATAMARCA',
    CORDOBA = 'CORDOBA'
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
