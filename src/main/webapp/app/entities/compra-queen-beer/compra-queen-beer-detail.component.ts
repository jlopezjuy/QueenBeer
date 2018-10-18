import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';

@Component({
    selector: 'jhi-compra-queen-beer-detail',
    templateUrl: './compra-queen-beer-detail.component.html'
})
export class CompraQueenBeerDetailComponent implements OnInit {
    compra: ICompraQueenBeer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compra }) => {
            this.compra = compra;
        });
    }

    previousState() {
        window.history.back();
    }
}
