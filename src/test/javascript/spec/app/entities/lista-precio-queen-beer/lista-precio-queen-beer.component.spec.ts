/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { QueenBeerTestModule } from '../../../test.module';
import { ListaPrecioQueenBeerComponent } from 'app/entities/lista-precio-queen-beer/lista-precio-queen-beer.component';
import { ListaPrecioQueenBeerService } from 'app/entities/lista-precio-queen-beer/lista-precio-queen-beer.service';
import { ListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';

describe('Component Tests', () => {
    describe('ListaPrecioQueenBeer Management Component', () => {
        let comp: ListaPrecioQueenBeerComponent;
        let fixture: ComponentFixture<ListaPrecioQueenBeerComponent>;
        let service: ListaPrecioQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ListaPrecioQueenBeerComponent],
                providers: []
            })
                .overrideTemplate(ListaPrecioQueenBeerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ListaPrecioQueenBeerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListaPrecioQueenBeerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ListaPrecioQueenBeer(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.listaPrecios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
