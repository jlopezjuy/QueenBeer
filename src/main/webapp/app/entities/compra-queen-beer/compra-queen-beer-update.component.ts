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
import { CompraInsumoQueenBeerService } from 'app/entities/compra-insumo-queen-beer';

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
        private insumoQueenBeerService: InsumoQueenBeerService,
        private compraInsumoQueenBeerService: CompraInsumoQueenBeerService
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compra }) => {
            this.compra = compra;
            if (compra !== undefined) {
                if (compra.id !== undefined) {
                    console.log(compra);
                    console.log(compra.id);
                    this.compraInsumoQueenBeerService.queryByCompraId(compra.id).subscribe(resp => {
                        this.compraInsumos = resp.body;
                        this.compraInsumos.forEach(compraInsumo => {
                            this.insumoQueenBeerService.find(compraInsumo.insumoId).subscribe(respIns => {
                                compraInsumo.insumoNombre = respIns.body.nombre;
                            });
                        });
                    });
                }
            }
        });
        this.loadAll();
        this.compraInsumo = new CompraInsumoQueenBeer();
        this.compra.impuestos = 0;
        this.compra.fechaCompra = moment(moment(new Date()).format('YYYY/MM/DD'));
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
        console.log('validate');
        let compInsumo = new CompraInsumoQueenBeer();
        compInsumo = this.compraInsumo;
        if (this.validateInsumo(compInsumo)) {
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
    }

    private subscribeToSaveInsumoResponse(result: Observable<HttpResponse<ICompraInsumoQueenBeer>>) {
        result.subscribe(
            (res: HttpResponse<ICompraQueenBeer>) => this.onSaveInsumoSuccess(res),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess(res: HttpResponse<ICompraQueenBeer>) {
        this.isSaving = false;
        this.saveInsumosCompra(res);
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onSaveInsumoSuccess(res: HttpResponse<ICompraInsumoQueenBeer>) {
        this.jhiAlertService.info('queenBeerApp.insumocreated', res.body.insumoId);
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    private saveInsumosCompra(res: HttpResponse<ICompraQueenBeer>) {
        const compra = res.body;
        this.compraInsumos.forEach(insumo => {
            insumo.compraId = compra.id;
            if (insumo.id !== undefined) {
                this.subscribeToSaveInsumoResponse(this.compraInsumoQueenBeerService.update(insumo));
            } else {
                this.subscribeToSaveInsumoResponse(this.compraInsumoQueenBeerService.create(insumo));
            }
        });
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICompraQueenBeer>>) {
        result.subscribe((res: HttpResponse<ICompraQueenBeer>) => this.onSaveSuccess(res), (res: HttpErrorResponse) => this.onSaveError());
    }

    /**
     * Method to validate supplies before insert on data table
     * @param insumo
     */
    validateInsumo(insumo: CompraInsumoQueenBeer) {
        console.log(insumo);
        if (insumo.cantidad === undefined) {
            this.jhiAlertService.error('queenBeerApp.compra.validate.insumos.undefined', null, null);
            return false;
        }
        if (insumo.cantidad < 0 || insumo.cantidad === undefined) {
            this.jhiAlertService.error('queenBeerApp.compra.validate.insumos.cantidad', null, null);
            return false;
        }
        if (insumo.costoTotal < 0 || insumo.costoTotal === undefined) {
            this.jhiAlertService.error('queenBeerApp.compra.validate.insumos.costo', null, null);
            return false;
        }
        return true;
    }

    /**
     * Validate final form before send data to backend
     */
    validateForm() {
        console.log('entro a validar');
        if (this.compraInsumos.length < 1) {
            this.jhiAlertService.error('queenBeerApp.compra.validate.insumos.required', null, null);
            return false;
        }
        return true;
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
            this.compra.total = this.compra.subtotal + aux;
        } else {
            this.compra.total = this.compra.subtotal;
        }
    }
}
