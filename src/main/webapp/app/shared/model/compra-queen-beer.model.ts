import { Moment } from 'moment';
import { ICompraInsumoQueenBeer } from 'app/shared/model//compra-insumo-queen-beer.model';

export interface ICompraQueenBeer {
    id?: number;
    fechaCompra?: Moment;
    fechaEntrega?: Moment;
    nroFactura?: string;
    subtotal?: number;
    impuestos?: number;
    total?: number;
    compraInsumos?: ICompraInsumoQueenBeer[];
}

export class CompraQueenBeer implements ICompraQueenBeer {
    constructor(
        public id?: number,
        public fechaCompra?: Moment,
        public fechaEntrega?: Moment,
        public nroFactura?: string,
        public subtotal?: number,
        public impuestos?: number,
        public total?: number,
        public compraInsumos?: ICompraInsumoQueenBeer[]
    ) {}
}
