/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ProductoQueenBeerUpdateComponent } from 'app/entities/producto-queen-beer/producto-queen-beer-update.component';
import { ProductoQueenBeerService } from 'app/entities/producto-queen-beer/producto-queen-beer.service';
import { ProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';

describe('Component Tests', () => {
    describe('ProductoQueenBeer Management Update Component', () => {
        let comp: ProductoQueenBeerUpdateComponent;
        let fixture: ComponentFixture<ProductoQueenBeerUpdateComponent>;
        let service: ProductoQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProductoQueenBeerUpdateComponent]
            })
                .overrideTemplate(ProductoQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductoQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductoQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductoQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.producto = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProductoQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.producto = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
