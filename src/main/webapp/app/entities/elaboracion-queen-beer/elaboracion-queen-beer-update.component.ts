import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IElaboracionQueenBeer, TipoMacerado } from 'app/shared/model/elaboracion-queen-beer.model';
import { ElaboracionQueenBeerService } from './elaboracion-queen-beer.service';
import { ElaboracionInsumoQueenBeer, IElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';
import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';
import { InsumoQueenBeerService } from 'app/entities/insumo-queen-beer';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-elaboracion-queen-beer-update',
    templateUrl: './elaboracion-queen-beer-update.component.html'
})
export class ElaboracionQueenBeerUpdateComponent implements OnInit {
    elaboracion: IElaboracionQueenBeer;
    elaboracionInsumo: string;
    isSaving: boolean;
    fechaInicio: string;
    fechaFin: string;
    inicioMacerado: string;
    insumosAgregado: ElaboracionInsumoQueenBeer[] = [];
    isMacerado: boolean;

    insumos: IInsumoQueenBeer[];

    constructor(
        private elaboracionService: ElaboracionQueenBeerService,
        private activatedRoute: ActivatedRoute,
        private insumoService: InsumoQueenBeerService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.isMacerado = false;
        this.activatedRoute.data.subscribe(({ elaboracion }) => {
            this.elaboracion = elaboracion;
            this.fechaInicio = this.elaboracion.fechaInicio != null ? this.elaboracion.fechaInicio.format(DATE_TIME_FORMAT) : null;
            this.fechaFin = this.elaboracion.fechaFin != null ? this.elaboracion.fechaFin.format(DATE_TIME_FORMAT) : null;
            this.inicioMacerado = this.elaboracion.inicioMacerado != null ? this.elaboracion.inicioMacerado.format(DATE_TIME_FORMAT) : null;
            this.changeMacerado();
        });
        this.insumoService.queryAllMalta().subscribe(
            (res: HttpResponse<IInsumoQueenBeer[]>) => {
                this.insumos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        const insumoAdd = new ElaboracionInsumoQueenBeer();
        insumoAdd.insumoId = +this.elaboracionInsumo;
        insumoAdd.isEditable = false;
        this.insumosAgregado.push(insumoAdd);
    }

    previousState() {
        window.history.back();
    }

    addInsumo() {
        console.log(this.elaboracionInsumo);
        // const insumoAdd = new ElaboracionInsumoQueenBeer();
        // insumoAdd.insumoId = +this.elaboracionInsumo;
        // insumoAdd.isEditable = false;
        // this.insumosAgregado.push(insumoAdd);
    }

    changeMacerado() {
        if (this.elaboracion.macerado === TipoMacerado.INFUCION) {
            this.isMacerado = true;
        } else {
            this.isMacerado = false;
        }
    }

    save() {
        this.isSaving = true;
        this.elaboracion.fechaInicio = this.fechaInicio != null ? moment(this.fechaInicio, DATE_TIME_FORMAT) : null;
        this.elaboracion.fechaFin = this.fechaFin != null ? moment(this.fechaFin, DATE_TIME_FORMAT) : null;
        this.elaboracion.inicioMacerado = this.inicioMacerado != null ? moment(this.inicioMacerado, DATE_TIME_FORMAT) : null;
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
