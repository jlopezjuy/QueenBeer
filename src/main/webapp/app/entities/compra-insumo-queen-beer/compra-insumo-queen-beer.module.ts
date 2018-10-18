import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    CompraInsumoQueenBeerComponent,
    CompraInsumoQueenBeerDetailComponent,
    CompraInsumoQueenBeerUpdateComponent,
    CompraInsumoQueenBeerDeletePopupComponent,
    CompraInsumoQueenBeerDeleteDialogComponent,
    compraInsumoRoute,
    compraInsumoPopupRoute
} from './';

const ENTITY_STATES = [...compraInsumoRoute, ...compraInsumoPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompraInsumoQueenBeerComponent,
        CompraInsumoQueenBeerDetailComponent,
        CompraInsumoQueenBeerUpdateComponent,
        CompraInsumoQueenBeerDeleteDialogComponent,
        CompraInsumoQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        CompraInsumoQueenBeerComponent,
        CompraInsumoQueenBeerUpdateComponent,
        CompraInsumoQueenBeerDeleteDialogComponent,
        CompraInsumoQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerCompraInsumoQueenBeerModule {}
