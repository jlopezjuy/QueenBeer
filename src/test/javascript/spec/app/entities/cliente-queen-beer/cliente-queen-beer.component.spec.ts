/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { QueenBeerTestModule } from '../../../test.module';
import { ClienteQueenBeerComponent } from 'app/entities/cliente-queen-beer/cliente-queen-beer.component';
import { ClienteQueenBeerService } from 'app/entities/cliente-queen-beer/cliente-queen-beer.service';
import { ClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';

describe('Component Tests', () => {
    describe('ClienteQueenBeer Management Component', () => {
        let comp: ClienteQueenBeerComponent;
        let fixture: ComponentFixture<ClienteQueenBeerComponent>;
        let service: ClienteQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ClienteQueenBeerComponent],
                providers: []
            })
                .overrideTemplate(ClienteQueenBeerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClienteQueenBeerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClienteQueenBeerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ClienteQueenBeer(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.clientes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
