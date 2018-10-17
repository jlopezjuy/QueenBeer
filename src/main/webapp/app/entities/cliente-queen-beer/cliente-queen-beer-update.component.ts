import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';
import { ClienteQueenBeerService } from './cliente-queen-beer.service';

@Component({
    selector: 'jhi-cliente-queen-beer-update',
    templateUrl: './cliente-queen-beer-update.component.html'
})
export class ClienteQueenBeerUpdateComponent implements OnInit {
    cliente: IClienteQueenBeer;
    isSaving: boolean;
    fechaAlta: string;

    constructor(private clienteService: ClienteQueenBeerService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cliente }) => {
            this.cliente = cliente;
            this.fechaAlta = this.cliente.fechaAlta != null ? this.cliente.fechaAlta.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cliente.fechaAlta = this.fechaAlta != null ? moment(this.fechaAlta, DATE_TIME_FORMAT) : null;
        if (this.cliente.id !== undefined) {
            this.subscribeToSaveResponse(this.clienteService.update(this.cliente));
        } else {
            this.subscribeToSaveResponse(this.clienteService.create(this.cliente));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IClienteQueenBeer>>) {
        result.subscribe((res: HttpResponse<IClienteQueenBeer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
