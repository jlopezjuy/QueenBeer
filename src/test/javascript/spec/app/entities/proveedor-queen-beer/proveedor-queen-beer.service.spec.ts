/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProveedorQueenBeerService } from 'app/entities/proveedor-queen-beer/proveedor-queen-beer.service';
import { IProveedorQueenBeer, ProveedorQueenBeer, CondicionFiscal, Provincia } from 'app/shared/model/proveedor-queen-beer.model';

describe('Service Tests', () => {
    describe('ProveedorQueenBeer Service', () => {
        let injector: TestBed;
        let service: ProveedorQueenBeerService;
        let httpMock: HttpTestingController;
        let elemDefault: IProveedorQueenBeer;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ProveedorQueenBeerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ProveedorQueenBeer(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                CondicionFiscal.RESPONSABLE_INSCRIPTO,
                'AAAAAAA',
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                0,
                'AAAAAAA',
                'AAAAAAA',
                Provincia.BUENOS_AIRES,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaAlta: currentDate.format(DATE_TIME_FORMAT)
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

            it('should create a ProveedorQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaAlta: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaAlta: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new ProveedorQueenBeer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ProveedorQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombreFantasia: 'BBBBBB',
                        razonSocial: 'BBBBBB',
                        cuit: 'BBBBBB',
                        condicionFiscal: 'BBBBBB',
                        telefono: 'BBBBBB',
                        fechaAlta: currentDate.format(DATE_TIME_FORMAT),
                        direccion: 'BBBBBB',
                        localidad: 'BBBBBB',
                        codigoPostal: 1,
                        contacto: 'BBBBBB',
                        email: 'BBBBBB',
                        provincia: 'BBBBBB',
                        notas: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        fechaAlta: currentDate
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

            it('should return a list of ProveedorQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombreFantasia: 'BBBBBB',
                        razonSocial: 'BBBBBB',
                        cuit: 'BBBBBB',
                        condicionFiscal: 'BBBBBB',
                        telefono: 'BBBBBB',
                        fechaAlta: currentDate.format(DATE_TIME_FORMAT),
                        direccion: 'BBBBBB',
                        localidad: 'BBBBBB',
                        codigoPostal: 1,
                        contacto: 'BBBBBB',
                        email: 'BBBBBB',
                        provincia: 'BBBBBB',
                        notas: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        fechaAlta: currentDate
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

            it('should delete a ProveedorQueenBeer', async () => {
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
