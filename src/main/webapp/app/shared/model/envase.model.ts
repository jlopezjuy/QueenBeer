export interface IEnvase {
    id?: number;
    nombre?: string;
    precio?: number;
    productoId?: number;
}

export class Envase implements IEnvase {
    constructor(public id?: number, public nombre?: string, public precio?: number, public productoId?: number) {}
}
