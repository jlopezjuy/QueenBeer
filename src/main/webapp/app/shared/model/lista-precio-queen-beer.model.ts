export interface IListaPrecioQueenBeer {
    id?: number;
    nombre?: string;
    porcentage?: number;
}

export class ListaPrecioQueenBeer implements IListaPrecioQueenBeer {
    constructor(public id?: number, public nombre?: string, public porcentage?: number) {}
}
