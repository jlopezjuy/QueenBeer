/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QueenBeerTestModule } from '../../../test.module';
import { FacturaVentaDeleteDialogComponent } from 'app/entities/factura-venta/factura-venta-delete-dialog.component';
import { FacturaVentaService } from 'app/entities/factura-venta/factura-venta.service';

describe('Component Tests', () => {
    describe('FacturaVenta Management Delete Component', () => {
        let comp: FacturaVentaDeleteDialogComponent;
        let fixture: ComponentFixture<FacturaVentaDeleteDialogComponent>;
        let service: FacturaVentaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [FacturaVentaDeleteDialogComponent]
            })
                .overrideTemplate(FacturaVentaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FacturaVentaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FacturaVentaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
