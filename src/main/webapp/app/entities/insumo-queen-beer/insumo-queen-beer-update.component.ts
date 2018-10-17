import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';
import { InsumoQueenBeerService } from './insumo-queen-beer.service';

@Component({
    selector: 'jhi-insumo-queen-beer-update',
    templateUrl: './insumo-queen-beer-update.component.html'
})
export class InsumoQueenBeerUpdateComponent implements OnInit {
    insumo: IInsumoQueenBeer;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private insumoService: InsumoQueenBeerService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ insumo }) => {
            this.insumo = insumo;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.insumo, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.insumo.id !== undefined) {
            this.subscribeToSaveResponse(this.insumoService.update(this.insumo));
        } else {
            this.subscribeToSaveResponse(this.insumoService.create(this.insumo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IInsumoQueenBeer>>) {
        result.subscribe((res: HttpResponse<IInsumoQueenBeer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
