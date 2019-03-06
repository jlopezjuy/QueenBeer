import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetalleVenta } from 'app/shared/model/detalle-venta.model';

type EntityResponseType = HttpResponse<IDetalleVenta>;
type EntityArrayResponseType = HttpResponse<IDetalleVenta[]>;

@Injectable({ providedIn: 'root' })
export class DetalleVentaService {
    public resourceUrl = SERVER_API_URL + 'api/detalle-ventas';
    public resourceUrlFactura = SERVER_API_URL + 'api/detalle-ventas/factura';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/detalle-ventas';

    constructor(protected http: HttpClient) {}

    create(detalleVenta: IDetalleVenta): Observable<EntityResponseType> {
        return this.http.post<IDetalleVenta>(this.resourceUrl, detalleVenta, { observe: 'response' });
    }

    update(detalleVenta: IDetalleVenta): Observable<EntityResponseType> {
        return this.http.put<IDetalleVenta>(this.resourceUrl, detalleVenta, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDetalleVenta>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetalleVenta[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetalleVenta[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }

    queryAllByFactura(facturaId: number, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetalleVenta[]>(`${this.resourceUrlFactura}/${facturaId}`, { params: options, observe: 'response' });
    }
}
