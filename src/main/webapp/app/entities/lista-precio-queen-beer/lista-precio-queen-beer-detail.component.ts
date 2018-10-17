import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';

@Component({
    selector: 'jhi-lista-precio-queen-beer-detail',
    templateUrl: './lista-precio-queen-beer-detail.component.html'
})
export class ListaPrecioQueenBeerDetailComponent implements OnInit {
    listaPrecio: IListaPrecioQueenBeer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ listaPrecio }) => {
            this.listaPrecio = listaPrecio;
        });
    }

    previousState() {
        window.history.back();
    }
}
