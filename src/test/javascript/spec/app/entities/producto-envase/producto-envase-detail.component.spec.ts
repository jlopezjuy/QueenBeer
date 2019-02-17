/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ProductoEnvaseDetailComponent } from 'app/entities/producto-envase/producto-envase-detail.component';
import { ProductoEnvase } from 'app/shared/model/producto-envase.model';

describe('Component Tests', () => {
    describe('ProductoEnvase Management Detail Component', () => {
        let comp: ProductoEnvaseDetailComponent;
        let fixture: ComponentFixture<ProductoEnvaseDetailComponent>;
        const route = ({ data: of({ productoEnvase: new ProductoEnvase(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProductoEnvaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProductoEnvaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductoEnvaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.productoEnvase).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
