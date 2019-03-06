/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { FacturaVentaDetailComponent } from 'app/entities/factura-venta/factura-venta-detail.component';
import { FacturaVenta } from 'app/shared/model/factura-venta.model';

describe('Component Tests', () => {
    describe('FacturaVenta Management Detail Component', () => {
        let comp: FacturaVentaDetailComponent;
        let fixture: ComponentFixture<FacturaVentaDetailComponent>;
        const route = ({ data: of({ facturaVenta: new FacturaVenta(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [FacturaVentaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FacturaVentaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FacturaVentaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.facturaVenta).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
