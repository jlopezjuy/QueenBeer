import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';

type EntityResponseType = HttpResponse<ICompraInsumoQueenBeer>;
type EntityArrayResponseType = HttpResponse<ICompraInsumoQueenBeer[]>;

@Injectable({ providedIn: 'root' })
export class CompraInsumoQueenBeerService {
    public resourceUrl = SERVER_API_URL + 'api/compra-insumos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/compra-insumos';

    constructor(private http: HttpClient) {}

    create(compraInsumo: ICompraInsumoQueenBeer): Observable<EntityResponseType> {
        return this.http.post<ICompraInsumoQueenBeer>(this.resourceUrl, compraInsumo, { observe: 'response' });
    }

    update(compraInsumo: ICompraInsumoQueenBeer): Observable<EntityResponseType> {
        return this.http.put<ICompraInsumoQueenBeer>(this.resourceUrl, compraInsumo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICompraInsumoQueenBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICompraInsumoQueenBeer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICompraInsumoQueenBeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
