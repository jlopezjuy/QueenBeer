export const enum UsoLupulo {
    BOIL = 'BOIL',
    FIRST_WORT = 'FIRST_WORT',
    DRY_HOP = 'DRY_HOP',
    AROMA = 'AROMA',
    MASH = 'MASH'
}

export const enum ModoLupulo {
    PELET = 'PELET',
    FLOR = 'FLOR'
}

export interface IElaboracionInsumoQueenBeer {
    id?: number;
    extracto?: number;
    color?: number;
    porcentage?: number;
    kilogramos?: number;
    uso?: UsoLupulo;
    alpha?: number;
    modo?: ModoLupulo;
    gramos?: number;
    gl?: number;
    tiempo?: number;
    ibu?: number;
    elaboracionId?: number;
    insumoId?: number;
}

export class ElaboracionInsumoQueenBeer implements IElaboracionInsumoQueenBeer {
    constructor(
        public id?: number,
        public extracto?: number,
        public color?: number,
        public porcentage?: number,
        public kilogramos?: number,
        public uso?: UsoLupulo,
        public alpha?: number,
        public modo?: ModoLupulo,
        public gramos?: number,
        public gl?: number,
        public tiempo?: number,
        public ibu?: number,
        public elaboracionId?: number,
        public insumoId?: number,
        public isEditable?: boolean
    ) {}
}
