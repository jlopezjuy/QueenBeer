import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';

@Component({
    selector: 'jhi-compra-insumo-queen-beer-detail',
    templateUrl: './compra-insumo-queen-beer-detail.component.html'
})
export class CompraInsumoQueenBeerDetailComponent implements OnInit {
    compraInsumo: ICompraInsumoQueenBeer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compraInsumo }) => {
            this.compraInsumo = compraInsumo;
        });
    }

    previousState() {
        window.history.back();
    }
}
