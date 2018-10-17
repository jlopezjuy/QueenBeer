import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';

type EntityResponseType = HttpResponse<IElaboracionQueenBeer>;
type EntityArrayResponseType = HttpResponse<IElaboracionQueenBeer[]>;

@Injectable({ providedIn: 'root' })
export class ElaboracionQueenBeerService {
    public resourceUrl = SERVER_API_URL + 'api/elaboracions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/elaboracions';

    constructor(private http: HttpClient) {}

    create(elaboracion: IElaboracionQueenBeer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(elaboracion);
        return this.http
            .post<IElaboracionQueenBeer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(elaboracion: IElaboracionQueenBeer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(elaboracion);
        return this.http
            .put<IElaboracionQueenBeer>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IElaboracionQueenBeer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IElaboracionQueenBeer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IElaboracionQueenBeer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(elaboracion: IElaboracionQueenBeer): IElaboracionQueenBeer {
        const copy: IElaboracionQueenBeer = Object.assign({}, elaboracion, {
            fechaInicio: elaboracion.fechaInicio != null && elaboracion.fechaInicio.isValid() ? elaboracion.fechaInicio.toJSON() : null,
            fechaFin: elaboracion.fechaFin != null && elaboracion.fechaFin.isValid() ? elaboracion.fechaFin.format(DATE_FORMAT) : null,
            inicioMacerado:
                elaboracion.inicioMacerado != null && elaboracion.inicioMacerado.isValid()
                    ? elaboracion.inicioMacerado.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.fechaInicio = res.body.fechaInicio != null ? moment(res.body.fechaInicio) : null;
        res.body.fechaFin = res.body.fechaFin != null ? moment(res.body.fechaFin) : null;
        res.body.inicioMacerado = res.body.inicioMacerado != null ? moment(res.body.inicioMacerado) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((elaboracion: IElaboracionQueenBeer) => {
            elaboracion.fechaInicio = elaboracion.fechaInicio != null ? moment(elaboracion.fechaInicio) : null;
            elaboracion.fechaFin = elaboracion.fechaFin != null ? moment(elaboracion.fechaFin) : null;
            elaboracion.inicioMacerado = elaboracion.inicioMacerado != null ? moment(elaboracion.inicioMacerado) : null;
        });
        return res;
    }
}
