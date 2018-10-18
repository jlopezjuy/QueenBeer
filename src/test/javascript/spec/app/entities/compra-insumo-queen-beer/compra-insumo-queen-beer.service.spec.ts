/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CompraInsumoQueenBeerService } from 'app/entities/compra-insumo-queen-beer/compra-insumo-queen-beer.service';
import { ICompraInsumoQueenBeer, CompraInsumoQueenBeer, Unidad } from 'app/shared/model/compra-insumo-queen-beer.model';

describe('Service Tests', () => {
    describe('CompraInsumoQueenBeer Service', () => {
        let injector: TestBed;
        let service: CompraInsumoQueenBeerService;
        let httpMock: HttpTestingController;
        let elemDefault: ICompraInsumoQueenBeer;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(CompraInsumoQueenBeerService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new CompraInsumoQueenBeer(0, 0, Unidad.KILOGRAMO, 'AAAAAAA', 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a CompraInsumoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new CompraInsumoQueenBeer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a CompraInsumoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        cantidad: 1,
                        unidad: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        costoTotal: 1
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of CompraInsumoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        cantidad: 1,
                        unidad: 'BBBBBB',
                        descripcion: 'BBBBBB',
                        costoTotal: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(take(1), map(resp => resp.body))
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a CompraInsumoQueenBeer', async () => {
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
