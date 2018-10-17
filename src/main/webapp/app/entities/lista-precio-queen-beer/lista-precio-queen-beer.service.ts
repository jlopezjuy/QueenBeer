import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';

type EntityResponseType = HttpResponse<IListaPrecioQueenBeer>;
type EntityArrayResponseType = HttpResponse<IListaPrecioQueenBeer[]>;

@Injectable({ providedIn: 'root' })
export class ListaPrecioQueenBeerService {
    public resourceUrl = SERVER_API_URL + 'api/lista-precios';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/lista-precios';

    constructor(private http: HttpClient) {}

    create(listaPrecio: IListaPrecioQueenBeer): Observable<EntityResponseType> {
        return this.http.post<IListaPrecioQueenBeer>(this.resourceUrl, listaPrecio, { observe: 'response' });
    }

    update(listaPrecio: IListaPrecioQueenBeer): Observable<EntityResponseType> {
        return this.http.put<IListaPrecioQueenBeer>(this.resourceUrl, listaPrecio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IListaPrecioQueenBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IListaPrecioQueenBeer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IListaPrecioQueenBeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
