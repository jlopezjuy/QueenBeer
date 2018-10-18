import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { ICompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';
import { CompraQueenBeerService } from './compra-queen-beer.service';

@Component({
    selector: 'jhi-compra-queen-beer-update',
    templateUrl: './compra-queen-beer-update.component.html'
})
export class CompraQueenBeerUpdateComponent implements OnInit {
    compra: ICompraQueenBeer;
    isSaving: boolean;
    fechaCompraDp: any;
    fechaEntregaDp: any;

    constructor(private compraService: CompraQueenBeerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ compra }) => {
            this.compra = compra;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.compra.id !== undefined) {
            this.subscribeToSaveResponse(this.compraService.update(this.compra));
        } else {
            this.subscribeToSaveResponse(this.compraService.create(this.compra));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICompraQueenBeer>>) {
        result.subscribe((res: HttpResponse<ICompraQueenBeer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
