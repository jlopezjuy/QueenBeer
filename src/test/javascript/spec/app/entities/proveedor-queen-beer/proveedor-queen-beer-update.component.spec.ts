/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ProveedorQueenBeerUpdateComponent } from 'app/entities/proveedor-queen-beer/proveedor-queen-beer-update.component';
import { ProveedorQueenBeerService } from 'app/entities/proveedor-queen-beer/proveedor-queen-beer.service';
import { ProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';

describe('Component Tests', () => {
    describe('ProveedorQueenBeer Management Update Component', () => {
        let comp: ProveedorQueenBeerUpdateComponent;
        let fixture: ComponentFixture<ProveedorQueenBeerUpdateComponent>;
        let service: ProveedorQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProveedorQueenBeerUpdateComponent]
            })
                .overrideTemplate(ProveedorQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProveedorQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProveedorQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ProveedorQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.proveedor = entity;
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
                    const entity = new ProveedorQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.proveedor = entity;
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
