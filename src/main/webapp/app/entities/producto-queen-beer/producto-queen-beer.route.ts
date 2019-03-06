import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';
import { ProductoQueenBeerService } from './producto-queen-beer.service';
import { ProductoQueenBeerComponent } from './producto-queen-beer.component';
import { ProductoQueenBeerDetailComponent } from './producto-queen-beer-detail.component';
import { ProductoQueenBeerUpdateComponent } from './producto-queen-beer-update.component';
import { ProductoQueenBeerDeletePopupComponent } from './producto-queen-beer-delete-dialog.component';
import { IProductoQueenBeer } from 'app/shared/model/producto-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class ProductoQueenBeerResolve implements Resolve<IProductoQueenBeer> {
    constructor(private service: ProductoQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductoQueenBeer> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductoQueenBeer>) => response.ok),
                map((producto: HttpResponse<ProductoQueenBeer>) => producto.body)
            );
        }
        return of(new ProductoQueenBeer());
    }
}

export const productoRoute: Routes = [
    {
        path: '',
        component: ProductoQueenBeerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.producto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductoQueenBeerDetailComponent,
        resolve: {
            producto: ProductoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.producto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductoQueenBeerUpdateComponent,
        resolve: {
            producto: ProductoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.producto.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductoQueenBeerUpdateComponent,
        resolve: {
            producto: ProductoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.producto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productoPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductoQueenBeerDeletePopupComponent,
        resolve: {
            producto: ProductoQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.producto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
