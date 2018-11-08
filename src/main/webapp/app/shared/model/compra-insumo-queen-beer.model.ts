export const enum Unidad {
    KILOGRAMO = 'KILOGRAMO',
    GRAMO = 'GRAMO',
    VEINTICINCO_KILOS = 'VEINTICINCO_KILOS',
    DIES_KILOS = 'DIES_KILOS',
    LITRO = 'LITRO',
    CM3 = 'CM3',
    ML = 'ML',
    UNIDAD = 'UNIDAD'
}

export interface ICompraInsumoQueenBeer {
    id?: number;
    cantidad?: number;
    unidad?: Unidad;
    descripcion?: string;
    costoTotal?: number;
    compraId?: number;
    insumoId?: number;
}

export class CompraInsumoQueenBeer implements ICompraInsumoQueenBeer {
    constructor(
        public id?: number,
        public cantidad?: number,
        public unidad?: Unidad,
        public descripcion?: string,
        public costoTotal?: number,
        public compraId?: number,
        public insumoId?: number,
        public insumoNombre?: string
    ) {}
}
