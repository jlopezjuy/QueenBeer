/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ElaboracionInsumoQueenBeerDetailComponent } from 'app/entities/elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer-detail.component';
import { ElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';

describe('Component Tests', () => {
    describe('ElaboracionInsumoQueenBeer Management Detail Component', () => {
        let comp: ElaboracionInsumoQueenBeerDetailComponent;
        let fixture: ComponentFixture<ElaboracionInsumoQueenBeerDetailComponent>;
        const route = ({ data: of({ elaboracionInsumo: new ElaboracionInsumoQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ElaboracionInsumoQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ElaboracionInsumoQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ElaboracionInsumoQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.elaboracionInsumo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
