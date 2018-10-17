import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';
import { ElaboracionInsumoQueenBeerService } from './elaboracion-insumo-queen-beer.service';
import { IElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';
import { ElaboracionQueenBeerService } from 'app/entities/elaboracion-queen-beer';
import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';
import { InsumoQueenBeerService } from 'app/entities/insumo-queen-beer';

@Component({
    selector: 'jhi-elaboracion-insumo-queen-beer-update',
    templateUrl: './elaboracion-insumo-queen-beer-update.component.html'
})
export class ElaboracionInsumoQueenBeerUpdateComponent implements OnInit {
    elaboracionInsumo: IElaboracionInsumoQueenBeer;
    isSaving: boolean;

    elaboracions: IElaboracionQueenBeer[];

    insumos: IInsumoQueenBeer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private elaboracionInsumoService: ElaboracionInsumoQueenBeerService,
        private elaboracionService: ElaboracionQueenBeerService,
        private insumoService: InsumoQueenBeerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ elaboracionInsumo }) => {
            this.elaboracionInsumo = elaboracionInsumo;
        });
        this.elaboracionService.query().subscribe(
            (res: HttpResponse<IElaboracionQueenBeer[]>) => {
                this.elaboracions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.insumoService.query().subscribe(
            (res: HttpResponse<IInsumoQueenBeer[]>) => {
                this.insumos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.elaboracionInsumo.id !== undefined) {
            this.subscribeToSaveResponse(this.elaboracionInsumoService.update(this.elaboracionInsumo));
        } else {
            this.subscribeToSaveResponse(this.elaboracionInsumoService.create(this.elaboracionInsumo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IElaboracionInsumoQueenBeer>>) {
        result.subscribe(
            (res: HttpResponse<IElaboracionInsumoQueenBeer>) => this.onSaveSuccess(),
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

    trackElaboracionById(index: number, item: IElaboracionQueenBeer) {
        return item.id;
    }

    trackInsumoById(index: number, item: IInsumoQueenBeer) {
        return item.id;
    }
}
