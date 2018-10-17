/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ListaPrecioQueenBeerUpdateComponent } from 'app/entities/lista-precio-queen-beer/lista-precio-queen-beer-update.component';
import { ListaPrecioQueenBeerService } from 'app/entities/lista-precio-queen-beer/lista-precio-queen-beer.service';
import { ListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';

describe('Component Tests', () => {
    describe('ListaPrecioQueenBeer Management Update Component', () => {
        let comp: ListaPrecioQueenBeerUpdateComponent;
        let fixture: ComponentFixture<ListaPrecioQueenBeerUpdateComponent>;
        let service: ListaPrecioQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ListaPrecioQueenBeerUpdateComponent]
            })
                .overrideTemplate(ListaPrecioQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ListaPrecioQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ListaPrecioQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ListaPrecioQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.listaPrecio = entity;
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
                    const entity = new ListaPrecioQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.listaPrecio = entity;
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
