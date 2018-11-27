/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ClienteQueenBeerService } from 'app/entities/cliente-queen-beer/cliente-queen-beer.service';
import {
    IClienteQueenBeer,
    ClienteQueenBeer,
    CategoriaCliente,
    CondicionFiscal,
    Provincia
} from 'app/shared/model/cliente-queen-beer.model';

describe('Service Tests', () => {
    describe('ClienteQueenBeer Service', () => {
        let injector: TestBed;
        let service: ClienteQueenBeerService;
        let httpMock: HttpTestingController;
        let elemDefault: IClienteQueenBeer;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ClienteQueenBeerService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new ClienteQueenBeer(
                0,
                'AAAAAAA',
                'AAAAAAA',
                CategoriaCliente.BAR,
                'AAAAAAA',
                CondicionFiscal.RESPONSABLE_INSCRIPTO,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                Provincia.MISIONES,
                0,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        fechaAlta: currentDate.format(DATE_FORMAT)
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

            it('should create a ClienteQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        fechaAlta: currentDate.format(DATE_FORMAT)
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
                    .create(new ClienteQueenBeer(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a ClienteQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombreFantasia: 'BBBBBB',
                        razonSocial: 'BBBBBB',
                        categoria: 'BBBBBB',
                        cuit: 'BBBBBB',
                        condicionFiscal: 'BBBBBB',
                        fechaAlta: currentDate.format(DATE_FORMAT),
                        telefono: 'BBBBBB',
                        direccion: 'BBBBBB',
                        localidadCiudad: 'BBBBBB',
                        privincia: 'BBBBBB',
                        codigoPostal: 1,
                        email: 'BBBBBB'
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

            it('should return a list of ClienteQueenBeer', async () => {
                const returnedFromService = Object.assign(
                    {
                        nombreFantasia: 'BBBBBB',
                        razonSocial: 'BBBBBB',
                        categoria: 'BBBBBB',
                        cuit: 'BBBBBB',
                        condicionFiscal: 'BBBBBB',
                        fechaAlta: currentDate.format(DATE_FORMAT),
                        telefono: 'BBBBBB',
                        direccion: 'BBBBBB',
                        localidadCiudad: 'BBBBBB',
                        privincia: 'BBBBBB',
                        codigoPostal: 1,
                        email: 'BBBBBB'
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
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a ClienteQueenBeer', async () => {
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
