import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DetalleVenta } from 'app/shared/model/detalle-venta.model';
import { DetalleVentaService } from './detalle-venta.service';
import { DetalleVentaComponent } from './detalle-venta.component';
import { DetalleVentaDetailComponent } from './detalle-venta-detail.component';
import { DetalleVentaUpdateComponent } from './detalle-venta-update.component';
import { DetalleVentaDeletePopupComponent } from './detalle-venta-delete-dialog.component';
import { IDetalleVenta } from 'app/shared/model/detalle-venta.model';

@Injectable({ providedIn: 'root' })
export class DetalleVentaResolve implements Resolve<IDetalleVenta> {
    constructor(private service: DetalleVentaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDetalleVenta> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DetalleVenta>) => response.ok),
                map((detalleVenta: HttpResponse<DetalleVenta>) => detalleVenta.body)
            );
        }
        return of(new DetalleVenta());
    }
}

export const detalleVentaRoute: Routes = [
    {
        path: '',
        component: DetalleVentaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.detalleVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DetalleVentaDetailComponent,
        resolve: {
            detalleVenta: DetalleVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.detalleVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DetalleVentaUpdateComponent,
        resolve: {
            detalleVenta: DetalleVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.detalleVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DetalleVentaUpdateComponent,
        resolve: {
            detalleVenta: DetalleVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.detalleVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detalleVentaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DetalleVentaDeletePopupComponent,
        resolve: {
            detalleVenta: DetalleVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.detalleVenta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
