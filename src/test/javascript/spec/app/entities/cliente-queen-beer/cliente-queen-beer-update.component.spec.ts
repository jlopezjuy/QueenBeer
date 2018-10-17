/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ClienteQueenBeerUpdateComponent } from 'app/entities/cliente-queen-beer/cliente-queen-beer-update.component';
import { ClienteQueenBeerService } from 'app/entities/cliente-queen-beer/cliente-queen-beer.service';
import { ClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';

describe('Component Tests', () => {
    describe('ClienteQueenBeer Management Update Component', () => {
        let comp: ClienteQueenBeerUpdateComponent;
        let fixture: ComponentFixture<ClienteQueenBeerUpdateComponent>;
        let service: ClienteQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ClienteQueenBeerUpdateComponent]
            })
                .overrideTemplate(ClienteQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ClienteQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClienteQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ClienteQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cliente = entity;
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
                    const entity = new ClienteQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cliente = entity;
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
