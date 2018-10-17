/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { QueenBeerTestModule } from '../../../test.module';
import { InsumoQueenBeerComponent } from 'app/entities/insumo-queen-beer/insumo-queen-beer.component';
import { InsumoQueenBeerService } from 'app/entities/insumo-queen-beer/insumo-queen-beer.service';
import { InsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';

describe('Component Tests', () => {
    describe('InsumoQueenBeer Management Component', () => {
        let comp: InsumoQueenBeerComponent;
        let fixture: ComponentFixture<InsumoQueenBeerComponent>;
        let service: InsumoQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [InsumoQueenBeerComponent],
                providers: []
            })
                .overrideTemplate(InsumoQueenBeerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InsumoQueenBeerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsumoQueenBeerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new InsumoQueenBeer(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.insumos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
