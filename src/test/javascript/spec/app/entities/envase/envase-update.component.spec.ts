/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { EnvaseUpdateComponent } from 'app/entities/envase/envase-update.component';
import { EnvaseService } from 'app/entities/envase/envase.service';
import { Envase } from 'app/shared/model/envase.model';

describe('Component Tests', () => {
    describe('Envase Management Update Component', () => {
        let comp: EnvaseUpdateComponent;
        let fixture: ComponentFixture<EnvaseUpdateComponent>;
        let service: EnvaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [EnvaseUpdateComponent]
            })
                .overrideTemplate(EnvaseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(EnvaseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnvaseService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Envase(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.envase = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Envase();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.envase = entity;
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
