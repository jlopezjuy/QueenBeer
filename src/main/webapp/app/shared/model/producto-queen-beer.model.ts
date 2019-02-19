export const enum TipoProducto {
    FIJO = 'FIJO',
    ROTATIVO = 'ROTATIVO',
    ESPECIAL = 'ESPECIAL'
}

export interface IProductoQueenBeer {
    id?: number;
    codigo?: string;
    complementario?: string;
    estilo?: string;
    nombreComercial?: string;
    precioLitro?: number;
    tipoProducto?: TipoProducto;
    imagenContentType?: string;
    imagen?: any;
}

export class ProductoQueenBeer implements IProductoQueenBeer {
    constructor(
        public id?: number,
        public codigo?: string,
        public complementario?: string,
        public estilo?: string,
        public nombreComercial?: string,
        public precioLitro?: number,
        public tipoProducto?: TipoProducto,
        public imagenContentType?: string,
        public imagen?: any
    ) {}
}
