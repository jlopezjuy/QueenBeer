import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    ElaboracionQueenBeerComponent,
    ElaboracionQueenBeerDetailComponent,
    ElaboracionQueenBeerUpdateComponent,
    ElaboracionQueenBeerDeletePopupComponent,
    ElaboracionQueenBeerDeleteDialogComponent,
    elaboracionRoute,
    elaboracionPopupRoute
} from './';

const ENTITY_STATES = [...elaboracionRoute, ...elaboracionPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ElaboracionQueenBeerComponent,
        ElaboracionQueenBeerDetailComponent,
        ElaboracionQueenBeerUpdateComponent,
        ElaboracionQueenBeerDeleteDialogComponent,
        ElaboracionQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        ElaboracionQueenBeerComponent,
        ElaboracionQueenBeerUpdateComponent,
        ElaboracionQueenBeerDeleteDialogComponent,
        ElaboracionQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerElaboracionQueenBeerModule {}
