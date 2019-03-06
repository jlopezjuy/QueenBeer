import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QueenBeerSharedModule } from 'app/shared';
import {
    EnvaseComponent,
    EnvaseDetailComponent,
    EnvaseUpdateComponent,
    EnvaseDeletePopupComponent,
    EnvaseDeleteDialogComponent,
    envaseRoute,
    envasePopupRoute
} from './';

const ENTITY_STATES = [...envaseRoute, ...envasePopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [EnvaseComponent, EnvaseDetailComponent, EnvaseUpdateComponent, EnvaseDeleteDialogComponent, EnvaseDeletePopupComponent],
    entryComponents: [EnvaseComponent, EnvaseUpdateComponent, EnvaseDeleteDialogComponent, EnvaseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerEnvaseModule {}
