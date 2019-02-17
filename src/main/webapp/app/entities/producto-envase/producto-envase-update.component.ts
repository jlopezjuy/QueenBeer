import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProductoEnvase } from 'app/shared/model/producto-envase.model';
import { ProductoEnvaseService } from './producto-envase.service';
import { IProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';
import { ProductoQueenBeerService } from 'app/entities/producto-queen-beer';
import { IEnvase } from 'app/shared/model/envase.model';
import { EnvaseService } from 'app/entities/envase';

@Component({
    selector: 'jhi-producto-envase-update',
    templateUrl: './producto-envase-update.component.html'
})
export class ProductoEnvaseUpdateComponent implements OnInit {
    productoEnvase: IProductoEnvase;
    isSaving: boolean;

    productos: IProductoQueenBeer[];

    envases: IEnvase[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected productoEnvaseService: ProductoEnvaseService,
        protected productoService: ProductoQueenBeerService,
        protected envaseService: EnvaseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ productoEnvase }) => {
            this.productoEnvase = productoEnvase;
        });
        this.productoService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProductoQueenBeer[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProductoQueenBeer[]>) => response.body)
            )
            .subscribe((res: IProductoQueenBeer[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.envaseService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEnvase[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEnvase[]>) => response.body)
            )
            .subscribe((res: IEnvase[]) => (this.envases = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.productoEnvase.id !== undefined) {
            this.subscribeToSaveResponse(this.productoEnvaseService.update(this.productoEnvase));
        } else {
            this.subscribeToSaveResponse(this.productoEnvaseService.create(this.productoEnvase));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoEnvase>>) {
        result.subscribe((res: HttpResponse<IProductoEnvase>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackProductoById(index: number, item: IProductoQueenBeer) {
        return item.id;
    }

    trackEnvaseById(index: number, item: IEnvase) {
        return item.id;
    }
}
