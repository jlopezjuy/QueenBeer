/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ElaboracionQueenBeerUpdateComponent } from 'app/entities/elaboracion-queen-beer/elaboracion-queen-beer-update.component';
import { ElaboracionQueenBeerService } from 'app/entities/elaboracion-queen-beer/elaboracion-queen-beer.service';
import { ElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';

describe('Component Tests', () => {
    describe('ElaboracionQueenBeer Management Update Component', () => {
        let comp: ElaboracionQueenBeerUpdateComponent;
        let fixture: ComponentFixture<ElaboracionQueenBeerUpdateComponent>;
        let service: ElaboracionQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ElaboracionQueenBeerUpdateComponent]
            })
                .overrideTemplate(ElaboracionQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ElaboracionQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElaboracionQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ElaboracionQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.elaboracion = entity;
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
                    const entity = new ElaboracionQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.elaboracion = entity;
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
