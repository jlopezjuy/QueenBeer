/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { InsumoQueenBeerUpdateComponent } from 'app/entities/insumo-queen-beer/insumo-queen-beer-update.component';
import { InsumoQueenBeerService } from 'app/entities/insumo-queen-beer/insumo-queen-beer.service';
import { InsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';

describe('Component Tests', () => {
    describe('InsumoQueenBeer Management Update Component', () => {
        let comp: InsumoQueenBeerUpdateComponent;
        let fixture: ComponentFixture<InsumoQueenBeerUpdateComponent>;
        let service: InsumoQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [InsumoQueenBeerUpdateComponent]
            })
                .overrideTemplate(InsumoQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InsumoQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InsumoQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InsumoQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.insumo = entity;
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
                    const entity = new InsumoQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.insumo = entity;
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
