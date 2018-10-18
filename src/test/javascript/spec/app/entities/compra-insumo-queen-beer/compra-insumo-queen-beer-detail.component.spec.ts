/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { CompraInsumoQueenBeerDetailComponent } from 'app/entities/compra-insumo-queen-beer/compra-insumo-queen-beer-detail.component';
import { CompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';

describe('Component Tests', () => {
    describe('CompraInsumoQueenBeer Management Detail Component', () => {
        let comp: CompraInsumoQueenBeerDetailComponent;
        let fixture: ComponentFixture<CompraInsumoQueenBeerDetailComponent>;
        const route = ({ data: of({ compraInsumo: new CompraInsumoQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [CompraInsumoQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompraInsumoQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompraInsumoQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.compraInsumo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
