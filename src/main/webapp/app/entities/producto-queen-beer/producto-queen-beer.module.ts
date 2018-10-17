import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    ProductoQueenBeerComponent,
    ProductoQueenBeerDetailComponent,
    ProductoQueenBeerUpdateComponent,
    ProductoQueenBeerDeletePopupComponent,
    ProductoQueenBeerDeleteDialogComponent,
    productoRoute,
    productoPopupRoute
} from './';

const ENTITY_STATES = [...productoRoute, ...productoPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductoQueenBeerComponent,
        ProductoQueenBeerDetailComponent,
        ProductoQueenBeerUpdateComponent,
        ProductoQueenBeerDeleteDialogComponent,
        ProductoQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        ProductoQueenBeerComponent,
        ProductoQueenBeerUpdateComponent,
        ProductoQueenBeerDeleteDialogComponent,
        ProductoQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerProductoQueenBeerModule {}
