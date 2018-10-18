/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { CompraInsumoQueenBeerUpdateComponent } from 'app/entities/compra-insumo-queen-beer/compra-insumo-queen-beer-update.component';
import { CompraInsumoQueenBeerService } from 'app/entities/compra-insumo-queen-beer/compra-insumo-queen-beer.service';
import { CompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';

describe('Component Tests', () => {
    describe('CompraInsumoQueenBeer Management Update Component', () => {
        let comp: CompraInsumoQueenBeerUpdateComponent;
        let fixture: ComponentFixture<CompraInsumoQueenBeerUpdateComponent>;
        let service: CompraInsumoQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [CompraInsumoQueenBeerUpdateComponent]
            })
                .overrideTemplate(CompraInsumoQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompraInsumoQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompraInsumoQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CompraInsumoQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.compraInsumo = entity;
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
                    const entity = new CompraInsumoQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.compraInsumo = entity;
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
