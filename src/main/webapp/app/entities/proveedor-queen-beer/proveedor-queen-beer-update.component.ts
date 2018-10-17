import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';
import { ProveedorQueenBeerService } from './proveedor-queen-beer.service';

@Component({
    selector: 'jhi-proveedor-queen-beer-update',
    templateUrl: './proveedor-queen-beer-update.component.html'
})
export class ProveedorQueenBeerUpdateComponent implements OnInit {
    proveedor: IProveedorQueenBeer;
    isSaving: boolean;
    fechaAlta: string;

    constructor(private proveedorService: ProveedorQueenBeerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ proveedor }) => {
            this.proveedor = proveedor;
            this.fechaAlta = this.proveedor.fechaAlta != null ? this.proveedor.fechaAlta.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.proveedor.fechaAlta = this.fechaAlta != null ? moment(this.fechaAlta, DATE_TIME_FORMAT) : null;
        if (this.proveedor.id !== undefined) {
            this.subscribeToSaveResponse(this.proveedorService.update(this.proveedor));
        } else {
            this.subscribeToSaveResponse(this.proveedorService.create(this.proveedor));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IProveedorQueenBeer>>) {
        result.subscribe((res: HttpResponse<IProveedorQueenBeer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
