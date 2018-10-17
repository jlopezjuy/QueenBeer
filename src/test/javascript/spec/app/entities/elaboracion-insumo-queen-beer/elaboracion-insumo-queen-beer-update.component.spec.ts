/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ElaboracionInsumoQueenBeerUpdateComponent } from 'app/entities/elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer-update.component';
import { ElaboracionInsumoQueenBeerService } from 'app/entities/elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer.service';
import { ElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';

describe('Component Tests', () => {
    describe('ElaboracionInsumoQueenBeer Management Update Component', () => {
        let comp: ElaboracionInsumoQueenBeerUpdateComponent;
        let fixture: ComponentFixture<ElaboracionInsumoQueenBeerUpdateComponent>;
        let service: ElaboracionInsumoQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ElaboracionInsumoQueenBeerUpdateComponent]
            })
                .overrideTemplate(ElaboracionInsumoQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ElaboracionInsumoQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElaboracionInsumoQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ElaboracionInsumoQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.elaboracionInsumo = entity;
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
                    const entity = new ElaboracionInsumoQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.elaboracionInsumo = entity;
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
