/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QueenBeerTestModule } from '../../../test.module';
import { ElaboracionQueenBeerDeleteDialogComponent } from 'app/entities/elaboracion-queen-beer/elaboracion-queen-beer-delete-dialog.component';
import { ElaboracionQueenBeerService } from 'app/entities/elaboracion-queen-beer/elaboracion-queen-beer.service';

describe('Component Tests', () => {
    describe('ElaboracionQueenBeer Management Delete Component', () => {
        let comp: ElaboracionQueenBeerDeleteDialogComponent;
        let fixture: ComponentFixture<ElaboracionQueenBeerDeleteDialogComponent>;
        let service: ElaboracionQueenBeerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ElaboracionQueenBeerDeleteDialogComponent]
            })
                .overrideTemplate(ElaboracionQueenBeerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ElaboracionQueenBeerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElaboracionQueenBeerService);
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
