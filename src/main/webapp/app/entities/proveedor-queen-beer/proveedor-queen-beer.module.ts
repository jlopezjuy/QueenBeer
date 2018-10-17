import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    ProveedorQueenBeerComponent,
    ProveedorQueenBeerDetailComponent,
    ProveedorQueenBeerUpdateComponent,
    ProveedorQueenBeerDeletePopupComponent,
    ProveedorQueenBeerDeleteDialogComponent,
    proveedorRoute,
    proveedorPopupRoute
} from './';

const ENTITY_STATES = [...proveedorRoute, ...proveedorPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProveedorQueenBeerComponent,
        ProveedorQueenBeerDetailComponent,
        ProveedorQueenBeerUpdateComponent,
        ProveedorQueenBeerDeleteDialogComponent,
        ProveedorQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        ProveedorQueenBeerComponent,
        ProveedorQueenBeerUpdateComponent,
        ProveedorQueenBeerDeleteDialogComponent,
        ProveedorQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerProveedorQueenBeerModule {}
