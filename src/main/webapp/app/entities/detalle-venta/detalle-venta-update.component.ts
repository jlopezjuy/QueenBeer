import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDetalleVenta } from 'app/shared/model/detalle-venta.model';
import { DetalleVentaService } from './detalle-venta.service';
import { IFacturaVenta } from 'app/shared/model/factura-venta.model';
import { FacturaVentaService } from 'app/entities/factura-venta';
import { IProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';
import { ProductoQueenBeerService } from 'app/entities/producto-queen-beer';

@Component({
    selector: 'jhi-detalle-venta-update',
    templateUrl: './detalle-venta-update.component.html'
})
export class DetalleVentaUpdateComponent implements OnInit {
    detalleVenta: IDetalleVenta;
    isSaving: boolean;

    facturaventas: IFacturaVenta[];

    productos: IProductoQueenBeer[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected detalleVentaService: DetalleVentaService,
        protected facturaVentaService: FacturaVentaService,
        protected productoService: ProductoQueenBeerService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ detalleVenta }) => {
            this.detalleVenta = detalleVenta;
        });
        this.facturaVentaService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IFacturaVenta[]>) => mayBeOk.ok),
                map((response: HttpResponse<IFacturaVenta[]>) => response.body)
            )
            .subscribe((res: IFacturaVenta[]) => (this.facturaventas = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.productoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductoQueenBeer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductoQueenBeer[]>) => response.body)
            )
            .subscribe((res: IProductoQueenBeer[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.detalleVenta.id !== undefined) {
            this.subscribeToSaveResponse(this.detalleVentaService.update(this.detalleVenta));
        } else {
            this.subscribeToSaveResponse(this.detalleVentaService.create(this.detalleVenta));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetalleVenta>>) {
        result.subscribe((res: HttpResponse<IDetalleVenta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackFacturaVentaById(index: number, item: IFacturaVenta) {
        return item.id;
    }

    trackProductoById(index: number, item: IProductoQueenBeer) {
        return item.id;
    }
}
