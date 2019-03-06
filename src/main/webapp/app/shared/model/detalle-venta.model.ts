export interface IDetalleVenta {
    id?: number;
    cantidad?: number;
    precioSubTotal?: number;
    facturaVentaId?: number;
    productoId?: number;
    envaseId?: number;
}

export class DetalleVenta implements IDetalleVenta {
    constructor(
        public id?: number,
        public cantidad?: number,
        public precioSubTotal?: number,
        public facturaVentaId?: number,
        public productoId?: number,
        public envaseId?: number
    ) {}
}
