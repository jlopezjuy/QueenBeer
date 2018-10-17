/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ElaboracionQueenBeerService } from 'app/entities/elaboracion-queen-beer/elaboracion-queen-beer.service';
import { IElaboracionQueenBeer, ElaboracionQueenBeer, TipoMacerado } from 'app/shared/model/elaboracion-queen-beer.model';

describe('Service Tests', () => {
    describe('ElaboracionQueenBeer Service', () => {
        let injector: TestBed;
        let service: ElaboracionQueenBeerService;
        let httpMock: HttpTestingController;
        let elemDefault: IElaboracionQueenBeer;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ElaboracionQueenBeerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ElaboracionQueenBeer(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                false,
                false,
                false,
                false,
                TipoMacerado.INFUCION,
                currentDate,
                false,
                false,
                false,
                false,
                0,
                0,
                0,
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaInicio: currentDate.format(DATE_TIME_FORMAT),
                        fechaFin: currentDate.format(DATE_TIME_FORMAT),
                        inicioMacerado: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a ElaboracionQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaInicio: currentDate.format(DATE_TIME_FORMAT),
                        fechaFin: currentDate.format(DATE_TIME_FORMAT),
                        inicioMacerado: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaInicio: currentDate,
                        fechaFin: currentDate,
                        inicioMacerado: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ElaboracionQueenBeer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ElaboracionQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        lote: 'BBBBBB',
                        nombre: 'BBBBBB',
                        estilo: 'BBBBBB',
                        fechaInicio: currentDate.format(DATE_TIME_FORMAT),
                        fechaFin: currentDate.format(DATE_TIME_FORMAT),
                        chequeo: true,
                        limpieza: true,
                        limpiezaOlla: true,
                        limpiezaManguera: true,
                        macerado: 'BBBBBB',
                        inicioMacerado: currentDate.format(DATE_TIME_FORMAT),
                        infucion: true,
                        primerEscalon: true,
                        segundoEscalon: true,
                        tercerEscalon: true,
                        litroInicial: 1,
                        relacionMaltaAgua: 1,
                        litroFalsoFondo: 1,
                        litroTotal: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaInicio: currentDate,
                        fechaFin: currentDate,
                        inicioMacerado: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of ElaboracionQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        lote: 'BBBBBB',
                        nombre: 'BBBBBB',
                        estilo: 'BBBBBB',
                        fechaInicio: currentDate.format(DATE_TIME_FORMAT),
                        fechaFin: currentDate.format(DATE_TIME_FORMAT),
                        chequeo: true,
                        limpieza: true,
                        limpiezaOlla: true,
                        limpiezaManguera: true,
                        macerado: 'BBBBBB',
                        inicioMacerado: currentDate.format(DATE_TIME_FORMAT),
                        infucion: true,
                        primerEscalon: true,
                        segundoEscalon: true,
                        tercerEscalon: true,
                        litroInicial: 1,
                        relacionMaltaAgua: 1,
                        litroFalsoFondo: 1,
                        litroTotal: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaInicio: currentDate,
                        fechaFin: currentDate,
                        inicioMacerado: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ElaboracionQueenBeer', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
