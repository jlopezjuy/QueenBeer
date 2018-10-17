import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { InsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';
import { InsumoQueenBeerService } from './insumo-queen-beer.service';
import { InsumoQueenBeerComponent } from './insumo-queen-beer.component';
import { InsumoQueenBeerDetailComponent } from './insumo-queen-beer-detail.component';
import { InsumoQueenBeerUpdateComponent } from './insumo-queen-beer-update.component';
import { InsumoQueenBeerDeletePopupComponent } from './insumo-queen-beer-delete-dialog.component';
import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class InsumoQueenBeerResolve implements Resolve<IInsumoQueenBeer> {
    constructor(private service: InsumoQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((insumo: HttpResponse<InsumoQueenBeer>) => insumo.body));
        }
        return of(new InsumoQueenBeer());
    }
}

export const insumoRoute: Routes = [
    {
        path: 'insumo-queen-beer',
        component: InsumoQueenBeerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'insumo-queen-beer/:id/view',
        component: InsumoQueenBeerDetailComponent,
        resolve: {
            insumo: InsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'insumo-queen-beer/new',
        component: InsumoQueenBeerUpdateComponent,
        resolve: {
            insumo: InsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'insumo-queen-beer/:id/edit',
        component: InsumoQueenBeerUpdateComponent,
        resolve: {
            insumo: InsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const insumoPopupRoute: Routes = [
    {
        path: 'insumo-queen-beer/:id/delete',
        component: InsumoQueenBeerDeletePopupComponent,
        resolve: {
            insumo: InsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.insumo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
