import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';
import { Principal } from 'app/core';
import { ElaboracionQueenBeerService } from './elaboracion-queen-beer.service';

@Component({
    selector: 'jhi-elaboracion-queen-beer',
    templateUrl: './elaboracion-queen-beer.component.html'
})
export class ElaboracionQueenBeerComponent implements OnInit, OnDestroy {
    elaboracions: IElaboracionQueenBeer[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private elaboracionService: ElaboracionQueenBeerService,
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
            this.elaboracionService
                .search({
                    query: this.currentSearch
                })
                .subscribe(
                    (res: HttpResponse<IElaboracionQueenBeer[]>) => (this.elaboracions = res.body),
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
        }
        this.elaboracionService.query().subscribe(
            (res: HttpResponse<IElaboracionQueenBeer[]>) => {
                this.elaboracions = res.body;
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
        this.registerChangeInElaboracions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IElaboracionQueenBeer) {
        return item.id;
    }

    registerChangeInElaboracions() {
        this.eventSubscriber = this.eventManager.subscribe('elaboracionListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
