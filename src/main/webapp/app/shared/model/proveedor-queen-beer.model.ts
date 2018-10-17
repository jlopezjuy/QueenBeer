import { Moment } from 'moment';

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

export interface IProveedorQueenBeer {
    id?: number;
    nombreFantasia?: string;
    razonSocial?: string;
    cuit?: string;
    condicionFiscal?: CondicionFiscal;
    telefono?: string;
    fechaAlta?: Moment;
    direccion?: string;
    localidad?: string;
    codigoPostal?: number;
    contacto?: string;
    email?: string;
    provincia?: Provincia;
    notas?: string;
}

export class ProveedorQueenBeer implements IProveedorQueenBeer {
    constructor(
        public id?: number,
        public nombreFantasia?: string,
        public razonSocial?: string,
        public cuit?: string,
        public condicionFiscal?: CondicionFiscal,
        public telefono?: string,
        public fechaAlta?: Moment,
        public direccion?: string,
        public localidad?: string,
        public codigoPostal?: number,
        public contacto?: string,
        public email?: string,
        public provincia?: Provincia,
        public notas?: string
    ) {}
}
