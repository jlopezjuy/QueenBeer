/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QueenBeerTestModule } from '../../../test.module';
import { ClienteQueenBeerDetailComponent } from 'app/entities/cliente-queen-beer/cliente-queen-beer-detail.component';
import { ClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';

describe('Component Tests', () => {
    describe('ClienteQueenBeer Management Detail Component', () => {
        let comp: ClienteQueenBeerDetailComponent;
        let fixture: ComponentFixture<ClienteQueenBeerDetailComponent>;
        const route = ({ data: of({ cliente: new ClienteQueenBeer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [QueenBeerTestModule],
                declarations: [ClienteQueenBeerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ClienteQueenBeerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ClienteQueenBeerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cliente).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
