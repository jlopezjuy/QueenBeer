import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    ListaPrecioQueenBeerComponent,
    ListaPrecioQueenBeerDetailComponent,
    ListaPrecioQueenBeerUpdateComponent,
    ListaPrecioQueenBeerDeletePopupComponent,
    ListaPrecioQueenBeerDeleteDialogComponent,
    listaPrecioRoute,
    listaPrecioPopupRoute
} from './';

const ENTITY_STATES = [...listaPrecioRoute, ...listaPrecioPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ListaPrecioQueenBeerComponent,
        ListaPrecioQueenBeerDetailComponent,
        ListaPrecioQueenBeerUpdateComponent,
        ListaPrecioQueenBeerDeleteDialogComponent,
        ListaPrecioQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        ListaPrecioQueenBeerComponent,
        ListaPrecioQueenBeerUpdateComponent,
        ListaPrecioQueenBeerDeleteDialogComponent,
        ListaPrecioQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerListaPrecioQueenBeerModule {}
