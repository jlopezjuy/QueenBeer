import { Component, OnInit, TemplateRef } from '@angular/core';
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
    compraInsumo: CompraInsumoQueenBeer;
    isSaving: boolean;
    fechaCompraDp: any;
    fechaEntregaDp: any;
    insumos: IInsumoQueenBeer[];
    compraInsumos: CompraInsumoQueenBeer[] = [];

    constructor(
        private compraService: CompraQueenBeerService,
        private activatedRoute: ActivatedRoute,
        private insumoService: InsumoQueenBeerService,
        private jhiAlertService: JhiAlertService,
        private insumoQueenBeerService: InsumoQueenBeerService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compra }) => {
            this.compra = compra;
        });
        this.loadAll();
        this.compraInsumo = new CompraInsumoQueenBeer();
        this.compra.impuestos = 0;
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
        if (this.validateForm()) {
            if (this.compra.id !== undefined) {
                this.subscribeToSaveResponse(this.compraService.update(this.compra));
            } else {
                this.subscribeToSaveResponse(this.compraService.create(this.compra));
            }
        } else {
            this.jhiAlertService.error('queenBeerApp.compra.validate.form');
            this.isSaving = false;
        }
    }

    saveInsumo() {
        let compInsumo = new CompraInsumoQueenBeer();
        compInsumo = this.compraInsumo;
        this.insumoQueenBeerService.find(this.compraInsumo.insumoId).subscribe(resp => {
            compInsumo.insumoNombre = resp.body.nombre;
            this.compraInsumos.push(compInsumo);
            this.compraInsumo = new CompraInsumoQueenBeer();
            let suma = 0;
            this.compraInsumos.forEach(insumo => {
                console.log(insumo);
                suma = suma + insumo.costoTotal;
            });
            this.reloadImpuesto();
        });
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

    validateForm() {
        console.log('entro a validar');
        return false;
    }

    reloadImpuesto() {
        let suma = 0;
        this.compraInsumos.forEach(insumo => {
            console.log(insumo);
            suma = suma + insumo.costoTotal;
        });
        this.compra.subtotal = suma;
        if (this.compra.impuestos > 0) {
            const aux = this.compra.subtotal * this.compra.impuestos / 100;
            console.log(aux);
            console.log(this.compra.subtotal);
            this.compra.total = this.compra.subtotal + aux;
        } else {
            this.compra.total = this.compra.subtotal;
        }
    }
}
