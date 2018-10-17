/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QueenBeerTestModule } from '../../../test.module';
import { ElaboracionInsumoQueenBeerDeleteDialogComponent } from 'app/entities/elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer-delete-dialog.component';
import { ElaboracionInsumoQueenBeerService } from 'app/entities/elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer.service';

describe('Component Tests', () => {
    describe('ElaboracionInsumoQueenBeer Management Delete Component', () => {
        let comp: ElaboracionInsumoQueenBeerDeleteDialogComponent;
        let fixture: ComponentFixture<ElaboracionInsumoQueenBeerDeleteDialogComponent>;
        let service: ElaboracionInsumoQueenBeerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ElaboracionInsumoQueenBeerDeleteDialogComponent]
            })
                .overrideTemplate(ElaboracionInsumoQueenBeerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ElaboracionInsumoQueenBeerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElaboracionInsumoQueenBeerService);
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
