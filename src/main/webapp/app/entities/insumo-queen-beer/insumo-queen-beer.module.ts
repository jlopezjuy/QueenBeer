import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    InsumoQueenBeerComponent,
    InsumoQueenBeerDetailComponent,
    InsumoQueenBeerUpdateComponent,
    InsumoQueenBeerDeletePopupComponent,
    InsumoQueenBeerDeleteDialogComponent,
    insumoRoute,
    insumoPopupRoute
} from './';

const ENTITY_STATES = [...insumoRoute, ...insumoPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InsumoQueenBeerComponent,
        InsumoQueenBeerDetailComponent,
        InsumoQueenBeerUpdateComponent,
        InsumoQueenBeerDeleteDialogComponent,
        InsumoQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        InsumoQueenBeerComponent,
        InsumoQueenBeerUpdateComponent,
        InsumoQueenBeerDeleteDialogComponent,
        InsumoQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerInsumoQueenBeerModule {}
