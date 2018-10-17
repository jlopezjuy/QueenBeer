/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { QueenBeerTestModule } from '../../../test.module';
import { ProductoQueenBeerComponent } from 'app/entities/producto-queen-beer/producto-queen-beer.component';
import { ProductoQueenBeerService } from 'app/entities/producto-queen-beer/producto-queen-beer.service';
import { ProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';

describe('Component Tests', () => {
    describe('ProductoQueenBeer Management Component', () => {
        let comp: ProductoQueenBeerComponent;
        let fixture: ComponentFixture<ProductoQueenBeerComponent>;
        let service: ProductoQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProductoQueenBeerComponent],
                providers: []
            })
                .overrideTemplate(ProductoQueenBeerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProductoQueenBeerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProductoQueenBeerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProductoQueenBeer(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.productos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
