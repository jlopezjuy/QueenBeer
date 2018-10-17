/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QueenBeerTestModule } from '../../../test.module';
import { ListaPrecioQueenBeerDeleteDialogComponent } from 'app/entities/lista-precio-queen-beer/lista-precio-queen-beer-delete-dialog.component';
import { ListaPrecioQueenBeerService } from 'app/entities/lista-precio-queen-beer/lista-precio-queen-beer.service';

describe('Component Tests', () => {
    describe('ListaPrecioQueenBeer Management Delete Component', () => {
        let comp: ListaPrecioQueenBeerDeleteDialogComponent;
        let fixture: ComponentFixture<ListaPrecioQueenBeerDeleteDialogComponent>;
        let service: ListaPrecioQueenBeerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ListaPrecioQueenBeerDeleteDialogComponent]
            })
                .overrideTemplate(ListaPrecioQueenBeerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ListaPrecioQueenBeerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListaPrecioQueenBeerService);
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
