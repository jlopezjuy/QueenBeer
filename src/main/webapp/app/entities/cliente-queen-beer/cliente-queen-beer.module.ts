import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    ClienteQueenBeerComponent,
    ClienteQueenBeerDetailComponent,
    ClienteQueenBeerUpdateComponent,
    ClienteQueenBeerDeletePopupComponent,
    ClienteQueenBeerDeleteDialogComponent,
    clienteRoute,
    clientePopupRoute
} from './';

const ENTITY_STATES = [...clienteRoute, ...clientePopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ClienteQueenBeerComponent,
        ClienteQueenBeerDetailComponent,
        ClienteQueenBeerUpdateComponent,
        ClienteQueenBeerDeleteDialogComponent,
        ClienteQueenBeerDeletePopupComponent
    ],
    entryComponents: [
        ClienteQueenBeerComponent,
        ClienteQueenBeerUpdateComponent,
        ClienteQueenBeerDeleteDialogComponent,
        ClienteQueenBeerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerClienteQueenBeerModule {}
