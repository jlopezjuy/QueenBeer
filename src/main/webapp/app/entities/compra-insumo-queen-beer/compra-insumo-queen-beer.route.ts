import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { CompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';
import { CompraInsumoQueenBeerService } from './compra-insumo-queen-beer.service';
import { CompraInsumoQueenBeerComponent } from './compra-insumo-queen-beer.component';
import { CompraInsumoQueenBeerDetailComponent } from './compra-insumo-queen-beer-detail.component';
import { CompraInsumoQueenBeerUpdateComponent } from './compra-insumo-queen-beer-update.component';
import { CompraInsumoQueenBeerDeletePopupComponent } from './compra-insumo-queen-beer-delete-dialog.component';
import { ICompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class CompraInsumoQueenBeerResolve implements Resolve<ICompraInsumoQueenBeer> {
    constructor(private service: CompraInsumoQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((compraInsumo: HttpResponse<CompraInsumoQueenBeer>) => compraInsumo.body));
        }
        return of(new CompraInsumoQueenBeer());
    }
}

export const compraInsumoRoute: Routes = [
    {
        path: 'compra-insumo-queen-beer',
        component: CompraInsumoQueenBeerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.compraInsumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra-insumo-queen-beer/:id/view',
        component: CompraInsumoQueenBeerDetailComponent,
        resolve: {
            compraInsumo: CompraInsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.compraInsumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra-insumo-queen-beer/new',
        component: CompraInsumoQueenBeerUpdateComponent,
        resolve: {
            compraInsumo: CompraInsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.compraInsumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'compra-insumo-queen-beer/:id/edit',
        component: CompraInsumoQueenBeerUpdateComponent,
        resolve: {
            compraInsumo: CompraInsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.compraInsumo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const compraInsumoPopupRoute: Routes = [
    {
        path: 'compra-insumo-queen-beer/:id/delete',
        component: CompraInsumoQueenBeerDeletePopupComponent,
        resolve: {
            compraInsumo: CompraInsumoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.compraInsumo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
