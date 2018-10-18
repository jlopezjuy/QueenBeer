/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CompraQueenBeerService } from 'app/entities/compra-queen-beer/compra-queen-beer.service';
import { ICompraQueenBeer, CompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';

describe('Service Tests', () => {
    describe('CompraQueenBeer Service', () => {
        let injector: TestBed;
        let service: CompraQueenBeerService;
        let httpMock: HttpTestingController;
        let elemDefault: ICompraQueenBeer;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CompraQueenBeerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new CompraQueenBeer(0, currentDate, currentDate, 'AAAAAAA', 0, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaCompra: currentDate.format(DATE_FORMAT),
                        fechaEntrega: currentDate.format(DATE_FORMAT)
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

            it('should create a CompraQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaCompra: currentDate.format(DATE_FORMAT),
                        fechaEntrega: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaCompra: currentDate,
                        fechaEntrega: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new CompraQueenBeer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CompraQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaCompra: currentDate.format(DATE_FORMAT),
                        fechaEntrega: currentDate.format(DATE_FORMAT),
                        nroFactura: 'BBBBBB',
                        subtotal: 1,
                        impuestos: 1,
                        total: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaCompra: currentDate,
                        fechaEntrega: currentDate
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

            it('should return a list of CompraQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaCompra: currentDate.format(DATE_FORMAT),
                        fechaEntrega: currentDate.format(DATE_FORMAT),
                        nroFactura: 'BBBBBB',
                        subtotal: 1,
                        impuestos: 1,
                        total: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaCompra: currentDate,
                        fechaEntrega: currentDate
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

            it('should delete a CompraQueenBeer', async () => {
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
