/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { ProductoQueenBeerService } from 'app/entities/producto-queen-beer/producto-queen-beer.service';
import { IProductoQueenBeer, ProductoQueenBeer, TipoProducto } from 'app/shared/model/producto-queen-beer.model';

describe('Service Tests', () => {
    describe('ProductoQueenBeer Service', () => {
        let injector: TestBed;
        let service: ProductoQueenBeerService;
        let httpMock: HttpTestingController;
        let elemDefault: IProductoQueenBeer;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProductoQueenBeerService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new ProductoQueenBeer(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                0,
                TipoProducto.FIJO,
                'image/png',
                'AAAAAAA',
                0
            );
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

            it('should create a ProductoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new ProductoQueenBeer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProductoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        codigo: 'BBBBBB',
                        complementario: 'BBBBBB',
                        estilo: 'BBBBBB',
                        nombreComercial: 'BBBBBB',
                        precioLitro: 1,
                        tipoProducto: 'BBBBBB',
                        imagen: 'BBBBBB',
                        cantidad: 1
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

            it('should return a list of ProductoQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        codigo: 'BBBBBB',
                        complementario: 'BBBBBB',
                        estilo: 'BBBBBB',
                        nombreComercial: 'BBBBBB',
                        precioLitro: 1,
                        tipoProducto: 'BBBBBB',
                        imagen: 'BBBBBB',
                        cantidad: 1
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ProductoQueenBeer', async () => {
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
