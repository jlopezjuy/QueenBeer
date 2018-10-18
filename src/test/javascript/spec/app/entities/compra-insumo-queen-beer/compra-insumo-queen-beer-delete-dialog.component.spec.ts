/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QueenBeerTestModule } from '../../../test.module';
import { CompraInsumoQueenBeerDeleteDialogComponent } from 'app/entities/compra-insumo-queen-beer/compra-insumo-queen-beer-delete-dialog.component';
import { CompraInsumoQueenBeerService } from 'app/entities/compra-insumo-queen-beer/compra-insumo-queen-beer.service';

describe('Component Tests', () => {
    describe('CompraInsumoQueenBeer Management Delete Component', () => {
        let comp: CompraInsumoQueenBeerDeleteDialogComponent;
        let fixture: ComponentFixture<CompraInsumoQueenBeerDeleteDialogComponent>;
        let service: CompraInsumoQueenBeerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [CompraInsumoQueenBeerDeleteDialogComponent]
            })
                .overrideTemplate(CompraInsumoQueenBeerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompraInsumoQueenBeerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompraInsumoQueenBeerService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
