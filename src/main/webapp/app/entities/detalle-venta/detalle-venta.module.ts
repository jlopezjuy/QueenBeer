import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { QueenBeerSharedModule } from 'app/shared';
import {
    DetalleVentaComponent,
    DetalleVentaDetailComponent,
    DetalleVentaUpdateComponent,
    DetalleVentaDeletePopupComponent,
    DetalleVentaDeleteDialogComponent,
    detalleVentaRoute,
    detalleVentaPopupRoute
} from './';

const ENTITY_STATES = [...detalleVentaRoute, ...detalleVentaPopupRoute];

@NgModule({
    imports: [QueenBeerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DetalleVentaComponent,
        DetalleVentaDetailComponent,
        DetalleVentaUpdateComponent,
        DetalleVentaDeleteDialogComponent,
        DetalleVentaDeletePopupComponent
    ],
    entryComponents: [
        DetalleVentaComponent,
        DetalleVentaUpdateComponent,
        DetalleVentaDeleteDialogComponent,
        DetalleVentaDeletePopupComponent
    ],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerDetalleVentaModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
