export const enum TipoEnvase {
    LATA_330 = 'LATA_330',
    LATA_473 = 'LATA_473',
    BOTELLA_330 = 'BOTELLA_330',
    BOTELLA_355 = 'BOTELLA_355',
    BOTELLA_500 = 'BOTELLA_500',
    BOTELLA_600 = 'BOTELLA_600',
    BOTELLA_1000 = 'BOTELLA_1000'
}

export interface IEnvase {
    id?: number;
    nombre?: string;
    precio?: number;
    productoId?: number;
}

export class Envase implements IEnvase {
    constructor(public id?: number, public nombre?: string, public precio?: number, public productoId?: number) {}
}
