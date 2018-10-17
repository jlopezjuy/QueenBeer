import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';

@Component({
    selector: 'jhi-cliente-queen-beer-detail',
    templateUrl: './cliente-queen-beer-detail.component.html'
})
export class ClienteQueenBeerDetailComponent implements OnInit {
    cliente: IClienteQueenBeer;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cliente }) => {
            this.cliente = cliente;
        });
    }

    previousState() {
        window.history.back();
    }
}
