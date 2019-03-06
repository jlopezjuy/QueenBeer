import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductoEnvase } from 'app/shared/model/producto-envase.model';

@Component({
    selector: 'jhi-producto-envase-detail',
    templateUrl: './producto-envase-detail.component.html'
})
export class ProductoEnvaseDetailComponent implements OnInit {
    productoEnvase: IProductoEnvase;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productoEnvase }) => {
            this.productoEnvase = productoEnvase;
        });
    }

    previousState() {
        window.history.back();
    }
}
