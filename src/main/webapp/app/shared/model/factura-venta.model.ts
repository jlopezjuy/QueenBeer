import { Moment } from 'moment';

export const enum TipoDocumento {
    FACTURA = 'FACTURA',
    REMITO = 'REMITO',
    PRESUPUESTO = 'PRESUPUESTO'
}

export const enum TipoMoneda {
    PESOS_ARGENTINOS = 'PESOS_ARGENTINOS',
    DOLARES = 'DOLARES'
}

export interface IFacturaVenta {
    id?: number;
    tipoDocumento?: TipoDocumento;
    fecha?: Moment;
    totalNeto?: number;
    tipoMoneda?: TipoMoneda;
    nroFactura?: string;
    clienteId?: number;
}

export class FacturaVenta implements IFacturaVenta {
    constructor(
        public id?: number,
        public tipoDocumento?: TipoDocumento,
        public fecha?: Moment,
        public totalNeto?: number,
        public tipoMoneda?: TipoMoneda,
        public nroFactura?: string,
        public clienteId?: number
    ) {}
}
