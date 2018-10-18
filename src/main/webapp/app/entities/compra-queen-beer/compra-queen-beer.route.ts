import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';
import { CompraQueenBeerService } from './compra-queen-beer.service';
import { CompraQueenBeerComponent } from './compra-queen-beer.component';
import { CompraQueenBeerDetailComponent } from './compra-queen-beer-detail.component';
import { CompraQueenBeerUpdateComponent } from './compra-queen-beer-update.component';
import { CompraQueenBeerDeletePopupComponent } from './compra-queen-beer-delete-dialog.component';
import { ICompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class CompraQueenBeerResolve implements Resolve<ICompraQueenBeer> {
    constructor(private service: CompraQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((compra: HttpResponse<CompraQueenBeer>) => compra.body));
        }
        return of(new CompraQueenBeer());
    }
}

export const compraRoute: Routes = [
    {
        path: 'compra-queen-beer',
        component: CompraQueenBeerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.compra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra-queen-beer/:id/view',
        component: CompraQueenBeerDetailComponent,
        resolve: {
            compra: CompraQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.compra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra-queen-beer/new',
        component: CompraQueenBeerUpdateComponent,
        resolve: {
            compra: CompraQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.compra.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra-queen-beer/:id/edit',
        component: CompraQueenBeerUpdateComponent,
        resolve: {
            compra: CompraQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.compra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compraPopupRoute: Routes = [
    {
        path: 'compra-queen-beer/:id/delete',
        component: CompraQueenBeerDeletePopupComponent,
        resolve: {
            compra: CompraQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.compra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
