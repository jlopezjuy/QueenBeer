import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';
import { ElaboracionQueenBeerService } from './elaboracion-queen-beer.service';

@Component({
    selector: 'jhi-elaboracion-queen-beer-update',
    templateUrl: './elaboracion-queen-beer-update.component.html'
})
export class ElaboracionQueenBeerUpdateComponent implements OnInit {
    elaboracion: IElaboracionQueenBeer;
    isSaving: boolean;
    fechaInicio: string;
    fechaFinDp: any;
    inicioMaceradoDp: any;

    constructor(private elaboracionService: ElaboracionQueenBeerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ elaboracion }) => {
            this.elaboracion = elaboracion;
            this.fechaInicio = this.elaboracion.fechaInicio != null ? this.elaboracion.fechaInicio.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.elaboracion.fechaInicio = this.fechaInicio != null ? moment(this.fechaInicio, DATE_TIME_FORMAT) : null;
        if (this.elaboracion.id !== undefined) {
            this.subscribeToSaveResponse(this.elaboracionService.update(this.elaboracion));
        } else {
            this.subscribeToSaveResponse(this.elaboracionService.create(this.elaboracion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IElaboracionQueenBeer>>) {
        result.subscribe(
            (res: HttpResponse<IElaboracionQueenBeer>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
