import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { QueenBeerSharedModule } from 'app/shared';
import {
    FacturaVentaComponent,
    FacturaVentaDetailComponent,
    FacturaVentaUpdateComponent,
    FacturaVentaDeletePopupComponent,
    FacturaVentaDeleteDialogComponent,
    facturaVentaRoute,
    facturaVentaPopupRoute
} from './';

const ENTITY_STATES = [...facturaVentaRoute, ...facturaVentaPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FacturaVentaComponent,
        FacturaVentaDetailComponent,
        FacturaVentaUpdateComponent,
        FacturaVentaDeleteDialogComponent,
        FacturaVentaDeletePopupComponent
    ],
    entryComponents: [
        FacturaVentaComponent,
        FacturaVentaUpdateComponent,
        FacturaVentaDeleteDialogComponent,
        FacturaVentaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerFacturaVentaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
