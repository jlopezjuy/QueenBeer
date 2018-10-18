import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    CompraQueenBeerComponent,
    CompraQueenBeerDetailComponent,
    CompraQueenBeerUpdateComponent,
    CompraQueenBeerDeletePopupComponent,
    CompraQueenBeerDeleteDialogComponent,
    compraRoute,
    compraPopupRoute
} from './';

const ENTITY_STATES = [...compraRoute, ...compraPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CompraQueenBeerComponent,
        CompraQueenBeerDetailComponent,
        CompraQueenBeerUpdateComponent,
        CompraQueenBeerDeleteDialogComponent,
        CompraQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        CompraQueenBeerComponent,
        CompraQueenBeerUpdateComponent,
        CompraQueenBeerDeleteDialogComponent,
        CompraQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerCompraQueenBeerModule {}
