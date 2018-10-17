/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ProveedorQueenBeerDetailComponent } from 'app/entities/proveedor-queen-beer/proveedor-queen-beer-detail.component';
import { ProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';

describe('Component Tests', () => {
    describe('ProveedorQueenBeer Management Detail Component', () => {
        let comp: ProveedorQueenBeerDetailComponent;
        let fixture: ComponentFixture<ProveedorQueenBeerDetailComponent>;
        const route = ({ data: of({ proveedor: new ProveedorQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProveedorQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProveedorQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProveedorQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.proveedor).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
