/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ProductoEnvaseUpdateComponent } from 'app/entities/producto-envase/producto-envase-update.component';
import { ProductoEnvaseService } from 'app/entities/producto-envase/producto-envase.service';
import { ProductoEnvase } from 'app/shared/model/producto-envase.model';

describe('Component Tests', () => {
    describe('ProductoEnvase Management Update Component', () => {
        let comp: ProductoEnvaseUpdateComponent;
        let fixture: ComponentFixture<ProductoEnvaseUpdateComponent>;
        let service: ProductoEnvaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProductoEnvaseUpdateComponent]
            })
                .overrideTemplate(ProductoEnvaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductoEnvaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductoEnvaseService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductoEnvase(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productoEnvase = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ProductoEnvase();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.productoEnvase = entity;
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
