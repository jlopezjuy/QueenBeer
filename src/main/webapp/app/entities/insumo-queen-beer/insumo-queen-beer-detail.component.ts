import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';

@Component({
    selector: 'jhi-insumo-queen-beer-detail',
    templateUrl: './insumo-queen-beer-detail.component.html'
})
export class InsumoQueenBeerDetailComponent implements OnInit {
    insumo: IInsumoQueenBeer;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ insumo }) => {
            this.insumo = insumo;
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
