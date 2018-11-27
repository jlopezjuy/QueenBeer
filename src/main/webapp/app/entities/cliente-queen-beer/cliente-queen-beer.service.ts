import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';

type EntityResponseType = HttpResponse<IClienteQueenBeer>;
type EntityArrayResponseType = HttpResponse<IClienteQueenBeer[]>;

@Injectable({ providedIn: 'root' })
export class ClienteQueenBeerService {
    public resourceUrl = SERVER_API_URL + 'api/clientes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/clientes';

    constructor(private http: HttpClient) {}

    create(cliente: IClienteQueenBeer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cliente);
        return this.http
            .post<IClienteQueenBeer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cliente: IClienteQueenBeer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cliente);
        return this.http
            .put<IClienteQueenBeer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IClienteQueenBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClienteQueenBeer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IClienteQueenBeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(cliente: IClienteQueenBeer): IClienteQueenBeer {
        const copy: IClienteQueenBeer = Object.assign({}, cliente, {
            fechaAlta: cliente.fechaAlta != null && cliente.fechaAlta.isValid() ? cliente.fechaAlta.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fechaAlta = res.body.fechaAlta != null ? moment(res.body.fechaAlta) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((cliente: IClienteQueenBeer) => {
                cliente.fechaAlta = cliente.fechaAlta != null ? moment(cliente.fechaAlta) : null;
            });
        }
        return res;
    }
}
