import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';

@Component({
    selector: 'jhi-elaboracion-insumo-queen-beer-detail',
    templateUrl: './elaboracion-insumo-queen-beer-detail.component.html'
})
export class ElaboracionInsumoQueenBeerDetailComponent implements OnInit {
    elaboracionInsumo: IElaboracionInsumoQueenBeer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ elaboracionInsumo }) => {
            this.elaboracionInsumo = elaboracionInsumo;
        });
    }

    previousState() {
        window.history.back();
    }
}
