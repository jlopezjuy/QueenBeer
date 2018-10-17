/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { QueenBeerTestModule } from '../../../test.module';
import { ElaboracionQueenBeerComponent } from 'app/entities/elaboracion-queen-beer/elaboracion-queen-beer.component';
import { ElaboracionQueenBeerService } from 'app/entities/elaboracion-queen-beer/elaboracion-queen-beer.service';
import { ElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';

describe('Component Tests', () => {
    describe('ElaboracionQueenBeer Management Component', () => {
        let comp: ElaboracionQueenBeerComponent;
        let fixture: ComponentFixture<ElaboracionQueenBeerComponent>;
        let service: ElaboracionQueenBeerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ElaboracionQueenBeerComponent],
                providers: []
            })
                .overrideTemplate(ElaboracionQueenBeerComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ElaboracionQueenBeerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ElaboracionQueenBeerService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ElaboracionQueenBeer(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.elaboracions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
