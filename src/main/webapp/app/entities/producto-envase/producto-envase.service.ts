import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductoEnvase } from 'app/shared/model/producto-envase.model';

type EntityResponseType = HttpResponse<IProductoEnvase>;
type EntityArrayResponseType = HttpResponse<IProductoEnvase[]>;

@Injectable({ providedIn: 'root' })
export class ProductoEnvaseService {
    public resourceUrl = SERVER_API_URL + 'api/producto-envases';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/producto-envases';

    constructor(protected http: HttpClient) {}

    create(productoEnvase: IProductoEnvase): Observable<EntityResponseType> {
        return this.http.post<IProductoEnvase>(this.resourceUrl, productoEnvase, { observe: 'response' });
    }

    update(productoEnvase: IProductoEnvase): Observable<EntityResponseType> {
        return this.http.put<IProductoEnvase>(this.resourceUrl, productoEnvase, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductoEnvase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductoEnvase[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductoEnvase[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
