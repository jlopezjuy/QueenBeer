import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';
import { ClienteQueenBeerService } from './cliente-queen-beer.service';
import { ClienteQueenBeerComponent } from './cliente-queen-beer.component';
import { ClienteQueenBeerDetailComponent } from './cliente-queen-beer-detail.component';
import { ClienteQueenBeerUpdateComponent } from './cliente-queen-beer-update.component';
import { ClienteQueenBeerDeletePopupComponent } from './cliente-queen-beer-delete-dialog.component';
import { IClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';

@Injectable({ providedIn: 'root' })
export class ClienteQueenBeerResolve implements Resolve<IClienteQueenBeer> {
    constructor(private service: ClienteQueenBeerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ClienteQueenBeer> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ClienteQueenBeer>) => response.ok),
                map((cliente: HttpResponse<ClienteQueenBeer>) => cliente.body)
            );
        }
        return of(new ClienteQueenBeer());
    }
}

export const clienteRoute: Routes = [
    {
        path: 'cliente-queen-beer',
        component: ClienteQueenBeerComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cliente-queen-beer/:id/view',
        component: ClienteQueenBeerDetailComponent,
        resolve: {
            cliente: ClienteQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cliente-queen-beer/new',
        component: ClienteQueenBeerUpdateComponent,
        resolve: {
            cliente: ClienteQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cliente-queen-beer/:id/edit',
        component: ClienteQueenBeerUpdateComponent,
        resolve: {
            cliente: ClienteQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clientePopupRoute: Routes = [
    {
        path: 'cliente-queen-beer/:id/delete',
        component: ClienteQueenBeerDeletePopupComponent,
        resolve: {
            cliente: ClienteQueenBeerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.cliente.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
