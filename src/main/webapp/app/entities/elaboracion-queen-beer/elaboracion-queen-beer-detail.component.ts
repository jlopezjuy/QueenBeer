import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';

@Component({
    selector: 'jhi-elaboracion-queen-beer-detail',
    templateUrl: './elaboracion-queen-beer-detail.component.html'
})
export class ElaboracionQueenBeerDetailComponent implements OnInit {
    elaboracion: IElaboracionQueenBeer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ elaboracion }) => {
            this.elaboracion = elaboracion;
        });
    }

    previousState() {
        window.history.back();
    }
}
