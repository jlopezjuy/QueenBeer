/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { InsumoQueenBeerDetailComponent } from 'app/entities/insumo-queen-beer/insumo-queen-beer-detail.component';
import { InsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';

describe('Component Tests', () => {
    describe('InsumoQueenBeer Management Detail Component', () => {
        let comp: InsumoQueenBeerDetailComponent;
        let fixture: ComponentFixture<InsumoQueenBeerDetailComponent>;
        const route = ({ data: of({ insumo: new InsumoQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [InsumoQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InsumoQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InsumoQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.insumo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
