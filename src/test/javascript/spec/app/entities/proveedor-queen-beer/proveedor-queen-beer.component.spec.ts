/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { QueenBeerTestModule } from '../../../test.module';
import { ProveedorQueenBeerComponent } from 'app/entities/proveedor-queen-beer/proveedor-queen-beer.component';
import { ProveedorQueenBeerService } from 'app/entities/proveedor-queen-beer/proveedor-queen-beer.service';
import { ProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';

describe('Component Tests', () => {
    describe('ProveedorQueenBeer Management Component', () => {
        let comp: ProveedorQueenBeerComponent;
        let fixture: ComponentFixture<ProveedorQueenBeerComponent>;
        let service: ProveedorQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ProveedorQueenBeerComponent],
                providers: []
            })
                .overrideTemplate(ProveedorQueenBeerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProveedorQueenBeerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProveedorQueenBeerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ProveedorQueenBeer(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.proveedors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
