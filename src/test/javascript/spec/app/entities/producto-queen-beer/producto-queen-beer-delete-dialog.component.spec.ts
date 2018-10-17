/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { QueenBeerTestModule } from '../../../test.module';
import { ProductoQueenBeerDeleteDialogComponent } from 'app/entities/producto-queen-beer/producto-queen-beer-delete-dialog.component';
import { ProductoQueenBeerService } from 'app/entities/producto-queen-beer/producto-queen-beer.service';

describe('Component Tests', () => {
    describe('ProductoQueenBeer Management Delete Component', () => {
        let comp: ProductoQueenBeerDeleteDialogComponent;
        let fixture: ComponentFixture<ProductoQueenBeerDeleteDialogComponent>;
        let service: ProductoQueenBeerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProductoQueenBeerDeleteDialogComponent]
            })
                .overrideTemplate(ProductoQueenBeerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProductoQueenBeerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductoQueenBeerService);
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
