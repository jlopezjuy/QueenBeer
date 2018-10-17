/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ListaPrecioQueenBeerDetailComponent } from 'app/entities/lista-precio-queen-beer/lista-precio-queen-beer-detail.component';
import { ListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';

describe('Component Tests', () => {
    describe('ListaPrecioQueenBeer Management Detail Component', () => {
        let comp: ListaPrecioQueenBeerDetailComponent;
        let fixture: ComponentFixture<ListaPrecioQueenBeerDetailComponent>;
        const route = ({ data: of({ listaPrecio: new ListaPrecioQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ListaPrecioQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ListaPrecioQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ListaPrecioQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.listaPrecio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
