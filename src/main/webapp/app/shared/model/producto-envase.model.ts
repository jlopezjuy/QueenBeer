export interface IProductoEnvase {
    id?: number;
    productoId?: number;
    envaseId?: number;
}

export class ProductoEnvase implements IProductoEnvase {
    constructor(public id?: number, public productoId?: number, public envaseId?: number) {}
}
