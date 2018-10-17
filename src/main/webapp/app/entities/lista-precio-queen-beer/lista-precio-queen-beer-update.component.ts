import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';
import { ListaPrecioQueenBeerService } from './lista-precio-queen-beer.service';

@Component({
    selector: 'jhi-lista-precio-queen-beer-update',
    templateUrl: './lista-precio-queen-beer-update.component.html'
})
export class ListaPrecioQueenBeerUpdateComponent implements OnInit {
    listaPrecio: IListaPrecioQueenBeer;
    isSaving: boolean;

    constructor(private listaPrecioService: ListaPrecioQueenBeerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ listaPrecio }) => {
            this.listaPrecio = listaPrecio;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.listaPrecio.id !== undefined) {
            this.subscribeToSaveResponse(this.listaPrecioService.update(this.listaPrecio));
        } else {
            this.subscribeToSaveResponse(this.listaPrecioService.create(this.listaPrecio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IListaPrecioQueenBeer>>) {
        result.subscribe(
            (res: HttpResponse<IListaPrecioQueenBeer>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
