import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { IProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';
import { ProductoQueenBeerService } from './producto-queen-beer.service';
import { Envase, IEnvase } from 'app/shared/model/envase.model';
import { EnvaseService } from 'app/entities/envase';

@Component({
    selector: 'jhi-producto-queen-beer-update',
    templateUrl: './producto-queen-beer-update.component.html'
})
export class ProductoQueenBeerUpdateComponent implements OnInit {
    producto: IProductoQueenBeer;
    envase: IEnvase;
    envases: IEnvase[];
    isSaving: boolean;
    isSavindEnvase: boolean;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected productoService: ProductoQueenBeerService,
        protected elementRef: ElementRef,
        private envaseService: EnvaseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ producto }) => {
            this.producto = producto;
            this.envase = new Envase();
            if (this.producto.id) {
                this.loadEnvases(this.producto.id);
            }
        });
        this.envases = [];
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.producto, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.producto.id !== undefined) {
            this.isSavindEnvase = false;
            this.subscribeToSaveResponse(this.productoService.update(this.producto));
        } else {
            this.isSavindEnvase = true;
            this.subscribeToSaveResponse(this.productoService.create(this.producto));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductoQueenBeer>>) {
        result.subscribe(
            (res: HttpResponse<IProductoQueenBeer>) => {
                this.onSaveSuccess();
                console.log(res);
                this.saveEnvases(res.body);
            },
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    addEnvase() {
        this.envases.push(this.envase);
        this.envase = new Envase();
    }

    protected saveEnvases(producto: IProductoQueenBeer) {
        this.envases.forEach(envase => {
            if (this.isSavindEnvase) {
                envase.productoId = producto.id;
                this.envaseService.create(envase).subscribe(resp => {
                    console.log('create');
                });
            } else {
                envase.productoId = producto.id;
                this.envaseService.update(envase).subscribe(
                    resp => {
                        console.log('update');
                    },
                    error => {
                        this.envaseService.create(envase).subscribe(resp => {
                            console.log('create');
                        });
                    }
                );
            }
        });
    }

    protected loadEnvases(productoId: number) {
        console.log('Producto Id: ' + productoId);
        this.envaseService.queryByProductoId(productoId).subscribe(resp => {
            this.envases = resp.body;
        });
    }
}
