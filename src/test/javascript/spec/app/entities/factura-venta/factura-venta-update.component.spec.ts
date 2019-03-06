/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { FacturaVentaUpdateComponent } from 'app/entities/factura-venta/factura-venta-update.component';
import { FacturaVentaService } from 'app/entities/factura-venta/factura-venta.service';
import { FacturaVenta } from 'app/shared/model/factura-venta.model';

describe('Component Tests', () => {
    describe('FacturaVenta Management Update Component', () => {
        let comp: FacturaVentaUpdateComponent;
        let fixture: ComponentFixture<FacturaVentaUpdateComponent>;
        let service: FacturaVentaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [FacturaVentaUpdateComponent]
            })
                .overrideTemplate(FacturaVentaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FacturaVentaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacturaVentaService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new FacturaVenta(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.facturaVenta = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new FacturaVenta();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.facturaVenta = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
