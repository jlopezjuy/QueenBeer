/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { CompraQueenBeerUpdateComponent } from 'app/entities/compra-queen-beer/compra-queen-beer-update.component';
import { CompraQueenBeerService } from 'app/entities/compra-queen-beer/compra-queen-beer.service';
import { CompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';

describe('Component Tests', () => {
    describe('CompraQueenBeer Management Update Component', () => {
        let comp: CompraQueenBeerUpdateComponent;
        let fixture: ComponentFixture<CompraQueenBeerUpdateComponent>;
        let service: CompraQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [CompraQueenBeerUpdateComponent]
            })
                .overrideTemplate(CompraQueenBeerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CompraQueenBeerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CompraQueenBeerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CompraQueenBeer(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.compra = entity;
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
                    const entity = new CompraQueenBeer();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.compra = entity;
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
