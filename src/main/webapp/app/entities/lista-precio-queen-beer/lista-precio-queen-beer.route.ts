import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';
import { ListaPrecioQueenBeerService } from './lista-precio-queen-beer.service';
import { ListaPrecioQueenBeerComponent } from './lista-precio-queen-beer.component';
import { ListaPrecioQueenBeerDetailComponent } from './lista-precio-queen-beer-detail.component';
import { ListaPrecioQueenBeerUpdateComponent } from './lista-precio-queen-beer-update.component';
import { ListaPrecioQueenBeerDeletePopupComponent } from './lista-precio-queen-beer-delete-dialog.component';
import { IListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class ListaPrecioQueenBeerResolve implements Resolve<IListaPrecioQueenBeer> {
    constructor(private service: ListaPrecioQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((listaPrecio: HttpResponse<ListaPrecioQueenBeer>) => listaPrecio.body));
        }
        return of(new ListaPrecioQueenBeer());
    }
}

export const listaPrecioRoute: Routes = [
    {
        path: 'lista-precio-queen-beer',
        component: ListaPrecioQueenBeerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.listaPrecio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lista-precio-queen-beer/:id/view',
        component: ListaPrecioQueenBeerDetailComponent,
        resolve: {
            listaPrecio: ListaPrecioQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.listaPrecio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lista-precio-queen-beer/new',
        component: ListaPrecioQueenBeerUpdateComponent,
        resolve: {
            listaPrecio: ListaPrecioQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.listaPrecio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'lista-precio-queen-beer/:id/edit',
        component: ListaPrecioQueenBeerUpdateComponent,
        resolve: {
            listaPrecio: ListaPrecioQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.listaPrecio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const listaPrecioPopupRoute: Routes = [
    {
        path: 'lista-precio-queen-beer/:id/delete',
        component: ListaPrecioQueenBeerDeletePopupComponent,
        resolve: {
            listaPrecio: ListaPrecioQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.listaPrecio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
