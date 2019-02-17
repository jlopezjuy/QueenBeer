import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacturaVenta } from 'app/shared/model/factura-venta.model';

@Component({
    selector: 'jhi-factura-venta-detail',
    templateUrl: './factura-venta-detail.component.html'
})
export class FacturaVentaDetailComponent implements OnInit {
    facturaVenta: IFacturaVenta;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ facturaVenta }) => {
            this.facturaVenta = facturaVenta;
        });
    }

    previousState() {
        window.history.back();
    }
}
