import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetalleVenta } from 'app/shared/model/detalle-venta.model';

@Component({
    selector: 'jhi-detalle-venta-detail',
    templateUrl: './detalle-venta-detail.component.html'
})
export class DetalleVentaDetailComponent implements OnInit {
    detalleVenta: IDetalleVenta;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detalleVenta }) => {
            this.detalleVenta = detalleVenta;
        });
    }

    previousState() {
        window.history.back();
    }
}
