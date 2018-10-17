import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';
import { ProveedorQueenBeerService } from './proveedor-queen-beer.service';
import { ProveedorQueenBeerComponent } from './proveedor-queen-beer.component';
import { ProveedorQueenBeerDetailComponent } from './proveedor-queen-beer-detail.component';
import { ProveedorQueenBeerUpdateComponent } from './proveedor-queen-beer-update.component';
import { ProveedorQueenBeerDeletePopupComponent } from './proveedor-queen-beer-delete-dialog.component';
import { IProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class ProveedorQueenBeerResolve implements Resolve<IProveedorQueenBeer> {
    constructor(private service: ProveedorQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((proveedor: HttpResponse<ProveedorQueenBeer>) => proveedor.body));
        }
        return of(new ProveedorQueenBeer());
    }
}

export const proveedorRoute: Routes = [
    {
        path: 'proveedor-queen-beer',
        component: ProveedorQueenBeerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'proveedor-queen-beer/:id/view',
        component: ProveedorQueenBeerDetailComponent,
        resolve: {
            proveedor: ProveedorQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'proveedor-queen-beer/new',
        component: ProveedorQueenBeerUpdateComponent,
        resolve: {
            proveedor: ProveedorQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'proveedor-queen-beer/:id/edit',
        component: ProveedorQueenBeerUpdateComponent,
        resolve: {
            proveedor: ProveedorQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const proveedorPopupRoute: Routes = [
    {
        path: 'proveedor-queen-beer/:id/delete',
        component: ProveedorQueenBeerDeletePopupComponent,
        resolve: {
            proveedor: ProveedorQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.proveedor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
