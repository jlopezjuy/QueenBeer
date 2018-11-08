/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QueenBeerTestModule } from '../../../test.module';
import { InsumoQueenBeerDeleteDialogComponent } from 'app/entities/insumo-queen-beer/insumo-queen-beer-delete-dialog.component';
import { InsumoQueenBeerService } from 'app/entities/insumo-queen-beer/insumo-queen-beer.service';

describe('Component Tests', () => {
    describe('InsumoQueenBeer Management Delete Component', () => {
        let comp: InsumoQueenBeerDeleteDialogComponent;
        let fixture: ComponentFixture<InsumoQueenBeerDeleteDialogComponent>;
        let service: InsumoQueenBeerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [InsumoQueenBeerDeleteDialogComponent]
            })
                .overrideTemplate(InsumoQueenBeerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InsumoQueenBeerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsumoQueenBeerService);
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