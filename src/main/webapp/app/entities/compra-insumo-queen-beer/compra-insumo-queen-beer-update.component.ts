import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';
import { CompraInsumoQueenBeerService } from './compra-insumo-queen-beer.service';
import { ICompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';
import { CompraQueenBeerService } from 'app/entities/compra-queen-beer';
import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';
import { InsumoQueenBeerService } from 'app/entities/insumo-queen-beer';

@Component({
    selector: 'jhi-compra-insumo-queen-beer-update',
    templateUrl: './compra-insumo-queen-beer-update.component.html'
})
export class CompraInsumoQueenBeerUpdateComponent implements OnInit {
    compraInsumo: ICompraInsumoQueenBeer;
    isSaving: boolean;

    compras: ICompraQueenBeer[];

    insumos: IInsumoQueenBeer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private compraInsumoService: CompraInsumoQueenBeerService,
        private compraService: CompraQueenBeerService,
        private insumoService: InsumoQueenBeerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compraInsumo }) => {
            this.compraInsumo = compraInsumo;
        });
        this.compraService.query().subscribe(
            (res: HttpResponse<ICompraQueenBeer[]>) => {
                this.compras = res.body;
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
        if (this.compraInsumo.id !== undefined) {
            this.subscribeToSaveResponse(this.compraInsumoService.update(this.compraInsumo));
        } else {
            this.subscribeToSaveResponse(this.compraInsumoService.create(this.compraInsumo));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICompraInsumoQueenBeer>>) {
        result.subscribe(
            (res: HttpResponse<ICompraInsumoQueenBeer>) => this.onSaveSuccess(),
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

    trackCompraById(index: number, item: ICompraQueenBeer) {
        return item.id;
    }

    trackInsumoById(index: number, item: IInsumoQueenBeer) {
        return item.id;
    }
}
