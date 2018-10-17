import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';
import { ElaboracionInsumoQueenBeerService } from './elaboracion-insumo-queen-beer.service';
import { ElaboracionInsumoQueenBeerComponent } from './elaboracion-insumo-queen-beer.component';
import { ElaboracionInsumoQueenBeerDetailComponent } from './elaboracion-insumo-queen-beer-detail.component';
import { ElaboracionInsumoQueenBeerUpdateComponent } from './elaboracion-insumo-queen-beer-update.component';
import { ElaboracionInsumoQueenBeerDeletePopupComponent } from './elaboracion-insumo-queen-beer-delete-dialog.component';
import { IElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class ElaboracionInsumoQueenBeerResolve implements Resolve<IElaboracionInsumoQueenBeer> {
    constructor(private service: ElaboracionInsumoQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((elaboracionInsumo: HttpResponse<ElaboracionInsumoQueenBeer>) => elaboracionInsumo.body));
        }
        return of(new ElaboracionInsumoQueenBeer());
    }
}

export const elaboracionInsumoRoute: Routes = [
    {
        path: 'elaboracion-insumo-queen-beer',
        component: ElaboracionInsumoQueenBeerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracionInsumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elaboracion-insumo-queen-beer/:id/view',
        component: ElaboracionInsumoQueenBeerDetailComponent,
        resolve: {
            elaboracionInsumo: ElaboracionInsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracionInsumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elaboracion-insumo-queen-beer/new',
        component: ElaboracionInsumoQueenBeerUpdateComponent,
        resolve: {
            elaboracionInsumo: ElaboracionInsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracionInsumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elaboracion-insumo-queen-beer/:id/edit',
        component: ElaboracionInsumoQueenBeerUpdateComponent,
        resolve: {
            elaboracionInsumo: ElaboracionInsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracionInsumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const elaboracionInsumoPopupRoute: Routes = [
    {
        path: 'elaboracion-insumo-queen-beer/:id/delete',
        component: ElaboracionInsumoQueenBeerDeletePopupComponent,
        resolve: {
            elaboracionInsumo: ElaboracionInsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracionInsumo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
