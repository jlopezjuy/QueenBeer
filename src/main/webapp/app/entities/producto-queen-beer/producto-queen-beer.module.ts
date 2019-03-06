import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

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
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerProductoQueenBeerModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
