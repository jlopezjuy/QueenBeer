/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ElaboracionQueenBeerDetailComponent } from 'app/entities/elaboracion-queen-beer/elaboracion-queen-beer-detail.component';
import { ElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';

describe('Component Tests', () => {
    describe('ElaboracionQueenBeer Management Detail Component', () => {
        let comp: ElaboracionQueenBeerDetailComponent;
        let fixture: ComponentFixture<ElaboracionQueenBeerDetailComponent>;
        const route = ({ data: of({ elaboracion: new ElaboracionQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ElaboracionQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ElaboracionQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ElaboracionQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.elaboracion).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
