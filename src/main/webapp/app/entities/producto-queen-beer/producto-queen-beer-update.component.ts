import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';
import { ProductoQueenBeerService } from './producto-queen-beer.service';

@Component({
    selector: 'jhi-producto-queen-beer-update',
    templateUrl: './producto-queen-beer-update.component.html'
})
export class ProductoQueenBeerUpdateComponent implements OnInit {
    producto: IProductoQueenBeer;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private productoService: ProductoQueenBeerService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ producto }) => {
            this.producto = producto;
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
        this.dataUtils.clearInputImage(this.producto, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.producto.id !== undefined) {
            this.subscribeToSaveResponse(this.productoService.update(this.producto));
        } else {
            this.subscribeToSaveResponse(this.productoService.create(this.producto));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProductoQueenBeer>>) {
        result.subscribe((res: HttpResponse<IProductoQueenBeer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
