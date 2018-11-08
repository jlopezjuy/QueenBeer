import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';

type EntityResponseType = HttpResponse<IProductoQueenBeer>;
type EntityArrayResponseType = HttpResponse<IProductoQueenBeer[]>;

@Injectable({ providedIn: 'root' })
export class ProductoQueenBeerService {
    public resourceUrl = SERVER_API_URL + 'api/productos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/productos';

    constructor(private http: HttpClient) {}

    create(producto: IProductoQueenBeer): Observable<EntityResponseType> {
        return this.http.post<IProductoQueenBeer>(this.resourceUrl, producto, { observe: 'response' });
    }

    update(producto: IProductoQueenBeer): Observable<EntityResponseType> {
        return this.http.put<IProductoQueenBeer>(this.resourceUrl, producto, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IProductoQueenBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductoQueenBeer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IProductoQueenBeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}