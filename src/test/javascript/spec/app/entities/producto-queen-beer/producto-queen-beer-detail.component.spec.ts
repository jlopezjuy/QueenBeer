/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ProductoQueenBeerDetailComponent } from 'app/entities/producto-queen-beer/producto-queen-beer-detail.component';
import { ProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';

describe('Component Tests', () => {
    describe('ProductoQueenBeer Management Detail Component', () => {
        let comp: ProductoQueenBeerDetailComponent;
        let fixture: ComponentFixture<ProductoQueenBeerDetailComponent>;
        const route = ({ data: of({ producto: new ProductoQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProductoQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductoQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductoQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.producto).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
