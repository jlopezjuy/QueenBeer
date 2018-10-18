/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { CompraQueenBeerDetailComponent } from 'app/entities/compra-queen-beer/compra-queen-beer-detail.component';
import { CompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';

describe('Component Tests', () => {
    describe('CompraQueenBeer Management Detail Component', () => {
        let comp: CompraQueenBeerDetailComponent;
        let fixture: ComponentFixture<CompraQueenBeerDetailComponent>;
        const route = ({ data: of({ compra: new CompraQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [CompraQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompraQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompraQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.compra).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
