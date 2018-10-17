import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';
import { Principal } from 'app/core';
import { ElaboracionInsumoQueenBeerService } from './elaboracion-insumo-queen-beer.service';

@Component({
    selector: 'jhi-elaboracion-insumo-queen-beer',
    templateUrl: './elaboracion-insumo-queen-beer.component.html'
})
export class ElaboracionInsumoQueenBeerComponent implements OnInit, OnDestroy {
    elaboracionInsumos: IElaboracionInsumoQueenBeer[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private elaboracionInsumoService: ElaboracionInsumoQueenBeerService,
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
            this.elaboracionInsumoService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IElaboracionInsumoQueenBeer[]>) => (this.elaboracionInsumos = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.elaboracionInsumoService.query().subscribe(
            (res: HttpResponse<IElaboracionInsumoQueenBeer[]>) => {
                this.elaboracionInsumos = res.body;
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
        this.registerChangeInElaboracionInsumos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IElaboracionInsumoQueenBeer) {
        return item.id;
    }

    registerChangeInElaboracionInsumos() {
        this.eventSubscriber = this.eventManager.subscribe('elaboracionInsumoListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
