/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { EnvaseDetailComponent } from 'app/entities/envase/envase-detail.component';
import { Envase } from 'app/shared/model/envase.model';

describe('Component Tests', () => {
    describe('Envase Management Detail Component', () => {
        let comp: EnvaseDetailComponent;
        let fixture: ComponentFixture<EnvaseDetailComponent>;
        const route = ({ data: of({ envase: new Envase(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [EnvaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(EnvaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(EnvaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.envase).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
