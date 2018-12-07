import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEnvase } from 'app/shared/model/envase.model';

type EntityResponseType = HttpResponse<IEnvase>;
type EntityArrayResponseType = HttpResponse<IEnvase[]>;

@Injectable({ providedIn: 'root' })
export class EnvaseService {
    public resourceUrl = SERVER_API_URL + 'api/envases';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/envases';

    constructor(private http: HttpClient) {}

    create(envase: IEnvase): Observable<EntityResponseType> {
        return this.http.post<IEnvase>(this.resourceUrl, envase, { observe: 'response' });
    }

    update(envase: IEnvase): Observable<EntityResponseType> {
        return this.http.put<IEnvase>(this.resourceUrl, envase, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEnvase>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEnvase[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEnvase[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
