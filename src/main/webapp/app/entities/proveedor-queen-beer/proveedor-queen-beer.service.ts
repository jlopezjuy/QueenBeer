import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';

type EntityResponseType = HttpResponse<IProveedorQueenBeer>;
type EntityArrayResponseType = HttpResponse<IProveedorQueenBeer[]>;

@Injectable({ providedIn: 'root' })
export class ProveedorQueenBeerService {
    public resourceUrl = SERVER_API_URL + 'api/proveedors';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/proveedors';

    constructor(private http: HttpClient) {}

    create(proveedor: IProveedorQueenBeer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(proveedor);
        return this.http
            .post<IProveedorQueenBeer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(proveedor: IProveedorQueenBeer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(proveedor);
        return this.http
            .put<IProveedorQueenBeer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProveedorQueenBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProveedorQueenBeer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProveedorQueenBeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(proveedor: IProveedorQueenBeer): IProveedorQueenBeer {
        const copy: IProveedorQueenBeer = Object.assign({}, proveedor, {
            fechaAlta: proveedor.fechaAlta != null && proveedor.fechaAlta.isValid() ? proveedor.fechaAlta.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaAlta = res.body.fechaAlta != null ? moment(res.body.fechaAlta) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((proveedor: IProveedorQueenBeer) => {
            proveedor.fechaAlta = proveedor.fechaAlta != null ? moment(proveedor.fechaAlta) : null;
        });
        return res;
    }
}
