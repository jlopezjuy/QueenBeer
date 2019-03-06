import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Envase } from 'app/shared/model/envase.model';
import { EnvaseService } from './envase.service';
import { EnvaseComponent } from './envase.component';
import { EnvaseDetailComponent } from './envase-detail.component';
import { EnvaseUpdateComponent } from './envase-update.component';
import { EnvaseDeletePopupComponent } from './envase-delete-dialog.component';
import { IEnvase } from 'app/shared/model/envase.model';

@Injectable({ providedIn: 'root' })
export class EnvaseResolve implements Resolve<IEnvase> {
    constructor(private service: EnvaseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Envase> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Envase>) => response.ok),
                map((envase: HttpResponse<Envase>) => envase.body)
            );
        }
        return of(new Envase());
    }
}

export const envaseRoute: Routes = [
    {
        path: 'envase',
        component: EnvaseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'queenBeerApp.envase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'envase/:id/view',
        component: EnvaseDetailComponent,
        resolve: {
            envase: EnvaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.envase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'envase/new',
        component: EnvaseUpdateComponent,
        resolve: {
            envase: EnvaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.envase.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'envase/:id/edit',
        component: EnvaseUpdateComponent,
        resolve: {
            envase: EnvaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.envase.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const envasePopupRoute: Routes = [
    {
        path: 'envase/:id/delete',
        component: EnvaseDeletePopupComponent,
        resolve: {
            envase: EnvaseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queenBeerApp.envase.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
