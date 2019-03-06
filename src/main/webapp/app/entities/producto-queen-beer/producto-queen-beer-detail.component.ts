import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';

@Component({
    selector: 'jhi-producto-queen-beer-detail',
    templateUrl: './producto-queen-beer-detail.component.html'
})
export class ProductoQueenBeerDetailComponent implements OnInit {
    producto: IProductoQueenBeer;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ producto }) => {
            this.producto = producto;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
