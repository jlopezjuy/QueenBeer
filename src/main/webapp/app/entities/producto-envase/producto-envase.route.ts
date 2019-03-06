import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoEnvase } from 'app/shared/model/producto-envase.model';
import { ProductoEnvaseService } from './producto-envase.service';
import { ProductoEnvaseComponent } from './producto-envase.component';
import { ProductoEnvaseDetailComponent } from './producto-envase-detail.component';
import { ProductoEnvaseUpdateComponent } from './producto-envase-update.component';
import { ProductoEnvaseDeletePopupComponent } from './producto-envase-delete-dialog.component';
import { IProductoEnvase } from 'app/shared/model/producto-envase.model';

@Injectable({ providedIn: 'root' })
export class ProductoEnvaseResolve implements Resolve<IProductoEnvase> {
    constructor(private service: ProductoEnvaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProductoEnvase> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ProductoEnvase>) => response.ok),
                map((productoEnvase: HttpResponse<ProductoEnvase>) => productoEnvase.body)
            );
        }
        return of(new ProductoEnvase());
    }
}

export const productoEnvaseRoute: Routes = [
    {
        path: '',
        component: ProductoEnvaseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.productoEnvase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProductoEnvaseDetailComponent,
        resolve: {
            productoEnvase: ProductoEnvaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.productoEnvase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProductoEnvaseUpdateComponent,
        resolve: {
            productoEnvase: ProductoEnvaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.productoEnvase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProductoEnvaseUpdateComponent,
        resolve: {
            productoEnvase: ProductoEnvaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.productoEnvase.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const productoEnvasePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProductoEnvaseDeletePopupComponent,
        resolve: {
            productoEnvase: ProductoEnvaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.productoEnvase.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
