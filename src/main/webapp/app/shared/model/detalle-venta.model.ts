export interface IDetalleVenta {
    id?: number;
    facturaVentaId?: number;
    productoId?: number;
}

export class DetalleVenta implements IDetalleVenta {
    constructor(public id?: number, public facturaVentaId?: number, public productoId?: number) {}
}
