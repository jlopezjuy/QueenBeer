/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ElaboracionInsumoQueenBeerService } from 'app/entities/elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer.service';
import {
    IElaboracionInsumoQueenBeer,
    ElaboracionInsumoQueenBeer,
    UsoLupulo,
    ModoLupulo
} from 'app/shared/model/elaboracion-insumo-queen-beer.model';

describe('Service Tests', () => {
    describe('ElaboracionInsumoQueenBeer Service', () => {
        let injector: TestBed;
        let service: ElaboracionInsumoQueenBeerService;
        let httpMock: HttpTestingController;
        let elemDefault: IElaboracionInsumoQueenBeer;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ElaboracionInsumoQueenBeerService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new ElaboracionInsumoQueenBeer(0, 0, 0, 0, 0, UsoLupulo.BOIL, 0, ModoLupulo.PELET, 0, 0, 0, 0);
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

            it('should create a ElaboracionInsumoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new ElaboracionInsumoQueenBeer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ElaboracionInsumoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        extracto: 1,
                        color: 1,
                        porcentage: 1,
                        kilogramos: 1,
                        uso: 'BBBBBB',
                        alpha: 1,
                        modo: 'BBBBBB',
                        gramos: 1,
                        gl: 1,
                        tiempo: 1,
                        ibu: 1
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

            it('should return a list of ElaboracionInsumoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        extracto: 1,
                        color: 1,
                        porcentage: 1,
                        kilogramos: 1,
                        uso: 'BBBBBB',
                        alpha: 1,
                        modo: 'BBBBBB',
                        gramos: 1,
                        gl: 1,
                        tiempo: 1,
                        ibu: 1
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

            it('should delete a ElaboracionInsumoQueenBeer', async () => {
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
