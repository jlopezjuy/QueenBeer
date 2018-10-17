import { Moment } from 'moment';
import { IElaboracionInsumoQueenBeer } from 'app/shared/model//elaboracion-insumo-queen-beer.model';

export const enum TipoMacerado {
    INFUCION = 'INFUCION',
    ESCALONADO = 'ESCALONADO'
}

export interface IElaboracionQueenBeer {
    id?: number;
    lote?: string;
    nombre?: string;
    estilo?: string;
    fechaInicio?: Moment;
    fechaFin?: Moment;
    chequeo?: boolean;
    limpieza?: boolean;
    limpiezaOlla?: boolean;
    limpiezaManguera?: boolean;
    macerado?: TipoMacerado;
    inicioMacerado?: Moment;
    infucion?: boolean;
    primerEscalon?: boolean;
    segundoEscalon?: boolean;
    tercerEscalon?: boolean;
    litroInicial?: number;
    relacionMaltaAgua?: number;
    litroFalsoFondo?: number;
    litroTotal?: number;
    elaboracionInsumos?: IElaboracionInsumoQueenBeer[];
}

export class ElaboracionQueenBeer implements IElaboracionQueenBeer {
    constructor(
        public id?: number,
        public lote?: string,
        public nombre?: string,
        public estilo?: string,
        public fechaInicio?: Moment,
        public fechaFin?: Moment,
        public chequeo?: boolean,
        public limpieza?: boolean,
        public limpiezaOlla?: boolean,
        public limpiezaManguera?: boolean,
        public macerado?: TipoMacerado,
        public inicioMacerado?: Moment,
        public infucion?: boolean,
        public primerEscalon?: boolean,
        public segundoEscalon?: boolean,
        public tercerEscalon?: boolean,
        public litroInicial?: number,
        public relacionMaltaAgua?: number,
        public litroFalsoFondo?: number,
        public litroTotal?: number,
        public elaboracionInsumos?: IElaboracionInsumoQueenBeer[]
    ) {
        this.chequeo = this.chequeo || false;
        this.limpieza = this.limpieza || false;
        this.limpiezaOlla = this.limpiezaOlla || false;
        this.limpiezaManguera = this.limpiezaManguera || false;
        this.infucion = this.infucion || false;
        this.primerEscalon = this.primerEscalon || false;
        this.segundoEscalon = this.segundoEscalon || false;
        this.tercerEscalon = this.tercerEscalon || false;
    }
}
