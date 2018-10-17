export interface IElaboracionInsumoQueenBeer {
    id?: number;
    elaboracionId?: number;
    insumoId?: number;
}

export class ElaboracionInsumoQueenBeer implements IElaboracionInsumoQueenBeer {
    constructor(public id?: number, public elaboracionId?: number, public insumoId?: number) {}
}
