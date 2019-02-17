import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IFacturaVenta } from 'app/shared/model/factura-venta.model';
import { FacturaVentaService } from './factura-venta.service';
import { IClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';
import { ClienteQueenBeerService } from 'app/entities/cliente-queen-beer';

@Component({
    selector: 'jhi-factura-venta-update',
    templateUrl: './factura-venta-update.component.html'
})
export class FacturaVentaUpdateComponent implements OnInit {
    facturaVenta: IFacturaVenta;
    isSaving: boolean;

    clientes: IClienteQueenBeer[];
    fechaDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected facturaVentaService: FacturaVentaService,
        protected clienteService: ClienteQueenBeerService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ facturaVenta }) => {
            this.facturaVenta = facturaVenta;
        });
        this.clienteService
            .query({ filter: 'facturaventa-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IClienteQueenBeer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IClienteQueenBeer[]>) => response.body)
            )
            .subscribe(
                (res: IClienteQueenBeer[]) => {
                    if (!this.facturaVenta.clienteId) {
                        this.clientes = res;
                    } else {
                        this.clienteService
                            .find(this.facturaVenta.clienteId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IClienteQueenBeer>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IClienteQueenBeer>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IClienteQueenBeer) => (this.clientes = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.facturaVenta.id !== undefined) {
            this.subscribeToSaveResponse(this.facturaVentaService.update(this.facturaVenta));
        } else {
            this.subscribeToSaveResponse(this.facturaVentaService.create(this.facturaVenta));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacturaVenta>>) {
        result.subscribe((res: HttpResponse<IFacturaVenta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackClienteById(index: number, item: IClienteQueenBeer) {
        return item.id;
    }
}
