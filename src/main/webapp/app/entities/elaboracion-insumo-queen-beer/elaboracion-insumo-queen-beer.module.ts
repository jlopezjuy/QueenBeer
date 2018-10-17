import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    ElaboracionInsumoQueenBeerComponent,
    ElaboracionInsumoQueenBeerDetailComponent,
    ElaboracionInsumoQueenBeerUpdateComponent,
    ElaboracionInsumoQueenBeerDeletePopupComponent,
    ElaboracionInsumoQueenBeerDeleteDialogComponent,
    elaboracionInsumoRoute,
    elaboracionInsumoPopupRoute
} from './';

const ENTITY_STATES = [...elaboracionInsumoRoute, ...elaboracionInsumoPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ElaboracionInsumoQueenBeerComponent,
        ElaboracionInsumoQueenBeerDetailComponent,
        ElaboracionInsumoQueenBeerUpdateComponent,
        ElaboracionInsumoQueenBeerDeleteDialogComponent,
        ElaboracionInsumoQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        ElaboracionInsumoQueenBeerComponent,
        ElaboracionInsumoQueenBeerUpdateComponent,
        ElaboracionInsumoQueenBeerDeleteDialogComponent,
        ElaboracionInsumoQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerElaboracionInsumoQueenBeerModule {}
