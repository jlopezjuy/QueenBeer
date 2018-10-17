import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';
import { ElaboracionQueenBeerService } from './elaboracion-queen-beer.service';
import { ElaboracionQueenBeerComponent } from './elaboracion-queen-beer.component';
import { ElaboracionQueenBeerDetailComponent } from './elaboracion-queen-beer-detail.component';
import { ElaboracionQueenBeerUpdateComponent } from './elaboracion-queen-beer-update.component';
import { ElaboracionQueenBeerDeletePopupComponent } from './elaboracion-queen-beer-delete-dialog.component';
import { IElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class ElaboracionQueenBeerResolve implements Resolve<IElaboracionQueenBeer> {
    constructor(private service: ElaboracionQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((elaboracion: HttpResponse<ElaboracionQueenBeer>) => elaboracion.body));
        }
        return of(new ElaboracionQueenBeer());
    }
}

export const elaboracionRoute: Routes = [
    {
        path: 'elaboracion-queen-beer',
        component: ElaboracionQueenBeerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elaboracion-queen-beer/:id/view',
        component: ElaboracionQueenBeerDetailComponent,
        resolve: {
            elaboracion: ElaboracionQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elaboracion-queen-beer/new',
        component: ElaboracionQueenBeerUpdateComponent,
        resolve: {
            elaboracion: ElaboracionQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracion.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'elaboracion-queen-beer/:id/edit',
        component: ElaboracionQueenBeerUpdateComponent,
        resolve: {
            elaboracion: ElaboracionQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const elaboracionPopupRoute: Routes = [
    {
        path: 'elaboracion-queen-beer/:id/delete',
        component: ElaboracionQueenBeerDeletePopupComponent,
        resolve: {
            elaboracion: ElaboracionQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.elaboracion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
