import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';

@Component({
    selector: 'jhi-proveedor-queen-beer-detail',
    templateUrl: './proveedor-queen-beer-detail.component.html'
})
export class ProveedorQueenBeerDetailComponent implements OnInit {
    proveedor: IProveedorQueenBeer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ proveedor }) => {
            this.proveedor = proveedor;
        });
    }

    previousState() {
        window.history.back();
    }
}
