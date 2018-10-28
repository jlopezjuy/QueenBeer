import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { ICompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';
import { CompraQueenBeerService } from './compra-queen-beer.service';
import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';
import { CompraInsumoQueenBeer, ICompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';
import { InsumoQueenBeerService } from 'app/entities/insumo-queen-beer';
import { JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-compra-queen-beer-update',
    templateUrl: './compra-queen-beer-update.component.html'
})
export class CompraQueenBeerUpdateComponent implements OnInit {
    compra: ICompraQueenBeer;
    isSaving: boolean;
    fechaCompraDp: any;
    fechaEntregaDp: any;
    insumos: IInsumoQueenBeer[];
    compraInsumos: ICompraInsumoQueenBeer[] = [];

    constructor(
        private compraService: CompraQueenBeerService,
        private activatedRoute: ActivatedRoute,
        private insumoService: InsumoQueenBeerService,
        private jhiAlertService: JhiAlertService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compra }) => {
            this.compra = compra;
        });
        const compraInsumo = new CompraInsumoQueenBeer();
        this.compraInsumos.push(compraInsumo);
        this.loadAll();
    }

    loadAll() {
        this.insumoService.queryAll().subscribe(
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
        if (this.compra.id !== undefined) {
            this.subscribeToSaveResponse(this.compraService.update(this.compra));
        } else {
            this.subscribeToSaveResponse(this.compraService.create(this.compra));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICompraQueenBeer>>) {
        result.subscribe((res: HttpResponse<ICompraQueenBeer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    public addNewInsumo(compraInsumoQueenBeer: CompraInsumoQueenBeer) {
        console.log('Entro a cambiar datos');
        // console.log(compraInsumoQueenBeer);
        if (compraInsumoQueenBeer.costoTotal !== null) {
            // let suma = 0;
            // this.compraInsumos.forEach(insumo => {
            //     console.log(insumo);
            //     suma = suma + insumo.costoTotal;
            // });
            // this.compra.subtotal = suma;
            // const compraInsumo = new CompraInsumoQueenBeer();
            // this.compraInsumos.push(compraInsumo);
            console.log(this.compra);
            console.log(this.compraInsumos);
        }
    }

    addFieldValue() {
        const compraInsumo = new CompraInsumoQueenBeer();
        this.compraInsumos.push(compraInsumo);
    }
}
