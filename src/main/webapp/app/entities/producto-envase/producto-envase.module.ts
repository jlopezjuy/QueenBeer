import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { QueenBeerSharedModule } from 'app/shared';
import {
    ProductoEnvaseComponent,
    ProductoEnvaseDetailComponent,
    ProductoEnvaseUpdateComponent,
    ProductoEnvaseDeletePopupComponent,
    ProductoEnvaseDeleteDialogComponent,
    productoEnvaseRoute,
    productoEnvasePopupRoute
} from './';

const ENTITY_STATES = [...productoEnvaseRoute, ...productoEnvasePopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ProductoEnvaseComponent,
        ProductoEnvaseDetailComponent,
        ProductoEnvaseUpdateComponent,
        ProductoEnvaseDeleteDialogComponent,
        ProductoEnvaseDeletePopupComponent
    ],
    entryComponents: [
        ProductoEnvaseComponent,
        ProductoEnvaseUpdateComponent,
        ProductoEnvaseDeleteDialogComponent,
        ProductoEnvaseDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerProductoEnvaseModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
