import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FacturaVenta } from 'app/shared/model/factura-venta.model';
import { FacturaVentaService } from './factura-venta.service';
import { FacturaVentaComponent } from './factura-venta.component';
import { FacturaVentaDetailComponent } from './factura-venta-detail.component';
import { FacturaVentaUpdateComponent } from './factura-venta-update.component';
import { FacturaVentaDeletePopupComponent } from './factura-venta-delete-dialog.component';
import { IFacturaVenta } from 'app/shared/model/factura-venta.model';

@Injectable({ providedIn: 'root' })
export class FacturaVentaResolve implements Resolve<IFacturaVenta> {
    constructor(private service: FacturaVentaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFacturaVenta> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FacturaVenta>) => response.ok),
                map((facturaVenta: HttpResponse<FacturaVenta>) => facturaVenta.body)
            );
        }
        return of(new FacturaVenta());
    }
}

export const facturaVentaRoute: Routes = [
    {
        path: '',
        component: FacturaVentaComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.facturaVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: FacturaVentaDetailComponent,
        resolve: {
            facturaVenta: FacturaVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.facturaVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: FacturaVentaUpdateComponent,
        resolve: {
            facturaVenta: FacturaVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.facturaVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: FacturaVentaUpdateComponent,
        resolve: {
            facturaVenta: FacturaVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.facturaVenta.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facturaVentaPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: FacturaVentaDeletePopupComponent,
        resolve: {
            facturaVenta: FacturaVentaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.facturaVenta.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
