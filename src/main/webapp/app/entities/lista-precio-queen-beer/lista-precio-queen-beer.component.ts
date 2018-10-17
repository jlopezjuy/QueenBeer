import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';
import { Principal } from 'app/core';
import { ListaPrecioQueenBeerService } from './lista-precio-queen-beer.service';

@Component({
    selector: 'jhi-lista-precio-queen-beer',
    templateUrl: './lista-precio-queen-beer.component.html'
})
export class ListaPrecioQueenBeerComponent implements OnInit, OnDestroy {
    listaPrecios: IListaPrecioQueenBeer[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private listaPrecioService: ListaPrecioQueenBeerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.listaPrecioService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IListaPrecioQueenBeer[]>) => (this.listaPrecios = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.listaPrecioService.query().subscribe(
            (res: HttpResponse<IListaPrecioQueenBeer[]>) => {
                this.listaPrecios = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInListaPrecios();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IListaPrecioQueenBeer) {
        return item.id;
    }

    registerChangeInListaPrecios() {
        this.eventSubscriber = this.eventManager.subscribe('listaPrecioListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
