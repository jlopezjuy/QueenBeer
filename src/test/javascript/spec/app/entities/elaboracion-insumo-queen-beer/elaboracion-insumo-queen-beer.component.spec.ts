/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { QueenBeerTestModule } from '../../../test.module';
import { ElaboracionInsumoQueenBeerComponent } from 'app/entities/elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer.component';
import { ElaboracionInsumoQueenBeerService } from 'app/entities/elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer.service';
import { ElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';

describe('Component Tests', () => {
    describe('ElaboracionInsumoQueenBeer Management Component', () => {
        let comp: ElaboracionInsumoQueenBeerComponent;
        let fixture: ComponentFixture<ElaboracionInsumoQueenBeerComponent>;
        let service: ElaboracionInsumoQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ElaboracionInsumoQueenBeerComponent],
                providers: []
            })
                .overrideTemplate(ElaboracionInsumoQueenBeerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ElaboracionInsumoQueenBeerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElaboracionInsumoQueenBeerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ElaboracionInsumoQueenBeer(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.elaboracionInsumos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
