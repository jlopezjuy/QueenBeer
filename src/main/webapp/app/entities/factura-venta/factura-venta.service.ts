import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFacturaVenta } from 'app/shared/model/factura-venta.model';

type EntityResponseType = HttpResponse<IFacturaVenta>;
type EntityArrayResponseType = HttpResponse<IFacturaVenta[]>;

@Injectable({ providedIn: 'root' })
export class FacturaVentaService {
    public resourceUrl = SERVER_API_URL + 'api/factura-ventas';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/factura-ventas';

    constructor(protected http: HttpClient) {}

    create(facturaVenta: IFacturaVenta): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(facturaVenta);
        return this.http
            .post<IFacturaVenta>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(facturaVenta: IFacturaVenta): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(facturaVenta);
        return this.http
            .put<IFacturaVenta>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IFacturaVenta>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFacturaVenta[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IFacturaVenta[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(facturaVenta: IFacturaVenta): IFacturaVenta {
        const copy: IFacturaVenta = Object.assign({}, facturaVenta, {
            fecha: facturaVenta.fecha != null && facturaVenta.fecha.isValid() ? facturaVenta.fecha.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.fecha = res.body.fecha != null ? moment(res.body.fecha) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((facturaVenta: IFacturaVenta) => {
                facturaVenta.fecha = facturaVenta.fecha != null ? moment(facturaVenta.fecha) : null;
            });
        }
        return res;
    }
}
