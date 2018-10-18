import { IElaboracionInsumoQueenBeer } from 'app/shared/model//elaboracion-insumo-queen-beer.model';
import { ICompraInsumoQueenBeer } from 'app/shared/model//compra-insumo-queen-beer.model';

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

export const enum TipoInsumo {
    MALTA = 'MALTA',
    LUPULO = 'LUPULO',
    LEVADURA = 'LEVADURA',
    ACIDO = 'ACIDO',
    SAL = 'SAL',
    OTROS = 'OTROS'
}

export interface IInsumoQueenBeer {
    id?: number;
    nombre?: string;
    marca?: string;
    stockMinimo?: number;
    unidad?: Unidad;
    tipo?: TipoInsumo;
    imagenContentType?: string;
    imagen?: any;
    elaboracionInsumos?: IElaboracionInsumoQueenBeer[];
    compraInsumos?: ICompraInsumoQueenBeer[];
}

export class InsumoQueenBeer implements IInsumoQueenBeer {
    constructor(
        public id?: number,
        public nombre?: string,
        public marca?: string,
        public stockMinimo?: number,
        public unidad?: Unidad,
        public tipo?: TipoInsumo,
        public imagenContentType?: string,
        public imagen?: any,
        public elaboracionInsumos?: IElaboracionInsumoQueenBeer[],
        public compraInsumos?: ICompraInsumoQueenBeer[]
    ) {}
}
