import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IEnvase } from 'app/shared/model/envase.model';
import { EnvaseService } from './envase.service';
import { IProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';
import { ProductoQueenBeerService } from 'app/entities/producto-queen-beer';

@Component({
    selector: 'jhi-envase-update',
    templateUrl: './envase-update.component.html'
})
export class EnvaseUpdateComponent implements OnInit {
    envase: IEnvase;
    isSaving: boolean;

    productos: IProductoQueenBeer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private envaseService: EnvaseService,
        private productoService: ProductoQueenBeerService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ envase }) => {
            this.envase = envase;
        });
        this.productoService.query().subscribe(
            (res: HttpResponse<IProductoQueenBeer[]>) => {
                this.productos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.envase.id !== undefined) {
            this.subscribeToSaveResponse(this.envaseService.update(this.envase));
        } else {
            this.subscribeToSaveResponse(this.envaseService.create(this.envase));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEnvase>>) {
        result.subscribe((res: HttpResponse<IEnvase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductoById(index: number, item: IProductoQueenBeer) {
        return item.id;
    }
}
