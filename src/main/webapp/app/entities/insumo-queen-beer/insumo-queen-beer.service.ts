import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';

type EntityResponseType = HttpResponse<IInsumoQueenBeer>;
type EntityArrayResponseType = HttpResponse<IInsumoQueenBeer[]>;

@Injectable({ providedIn: 'root' })
export class InsumoQueenBeerService {
    public resourceUrl = SERVER_API_URL + 'api/insumos';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/insumos';

    constructor(private http: HttpClient) {}

    create(insumo: IInsumoQueenBeer): Observable<EntityResponseType> {
        return this.http.post<IInsumoQueenBeer>(this.resourceUrl, insumo, { observe: 'response' });
    }

    update(insumo: IInsumoQueenBeer): Observable<EntityResponseType> {
        return this.http.put<IInsumoQueenBeer>(this.resourceUrl, insumo, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IInsumoQueenBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInsumoQueenBeer[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    queryAll(req?: any): Observable<EntityArrayResponseType> {
        return this.http.get<IInsumoQueenBeer[]>(this.resourceUrl + '/all', { observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IInsumoQueenBeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
