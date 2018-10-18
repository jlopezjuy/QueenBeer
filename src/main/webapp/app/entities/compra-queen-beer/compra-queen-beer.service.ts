import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';

type EntityResponseType = HttpResponse<ICompraQueenBeer>;
type EntityArrayResponseType = HttpResponse<ICompraQueenBeer[]>;

@Injectable({ providedIn: 'root' })
export class CompraQueenBeerService {
    public resourceUrl = SERVER_API_URL + 'api/compras';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/compras';

    constructor(private http: HttpClient) {}

    create(compra: ICompraQueenBeer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(compra);
        return this.http
            .post<ICompraQueenBeer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(compra: ICompraQueenBeer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(compra);
        return this.http
            .put<ICompraQueenBeer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICompraQueenBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICompraQueenBeer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICompraQueenBeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(compra: ICompraQueenBeer): ICompraQueenBeer {
        const copy: ICompraQueenBeer = Object.assign({}, compra, {
            fechaCompra: compra.fechaCompra != null && compra.fechaCompra.isValid() ? compra.fechaCompra.format(DATE_FORMAT) : null,
            fechaEntrega: compra.fechaEntrega != null && compra.fechaEntrega.isValid() ? compra.fechaEntrega.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaCompra = res.body.fechaCompra != null ? moment(res.body.fechaCompra) : null;
        res.body.fechaEntrega = res.body.fechaEntrega != null ? moment(res.body.fechaEntrega) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((compra: ICompraQueenBeer) => {
            compra.fechaCompra = compra.fechaCompra != null ? moment(compra.fechaCompra) : null;
            compra.fechaEntrega = compra.fechaEntrega != null ? moment(compra.fechaEntrega) : null;
        });
        return res;
    }
}
