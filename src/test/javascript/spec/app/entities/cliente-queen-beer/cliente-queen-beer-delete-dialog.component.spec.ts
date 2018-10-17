/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QueenBeerTestModule } from '../../../test.module';
import { ClienteQueenBeerDeleteDialogComponent } from 'app/entities/cliente-queen-beer/cliente-queen-beer-delete-dialog.component';
import { ClienteQueenBeerService } from 'app/entities/cliente-queen-beer/cliente-queen-beer.service';

describe('Component Tests', () => {
    describe('ClienteQueenBeer Management Delete Component', () => {
        let comp: ClienteQueenBeerDeleteDialogComponent;
        let fixture: ComponentFixture<ClienteQueenBeerDeleteDialogComponent>;
        let service: ClienteQueenBeerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ClienteQueenBeerDeleteDialogComponent]
            })
                .overrideTemplate(ClienteQueenBeerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClienteQueenBeerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClienteQueenBeerService);
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
