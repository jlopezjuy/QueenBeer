import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IListaPrecioQueenBeer } from 'app/shared/model/lista-precio-queen-beer.model';
import { ListaPrecioQueenBeerService } from './lista-precio-queen-beer.service';

@Component({
    selector: 'jhi-lista-precio-queen-beer-delete-dialog',
    templateUrl: './lista-precio-queen-beer-delete-dialog.component.html'
})
export class ListaPrecioQueenBeerDeleteDialogComponent {
    listaPrecio: IListaPrecioQueenBeer;

    constructor(
        private listaPrecioService: ListaPrecioQueenBeerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.listaPrecioService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'listaPrecioListModification',
                content: 'Deleted an listaPrecio'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-lista-precio-queen-beer-delete-popup',
    template: ''
})
export class ListaPrecioQueenBeerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ listaPrecio }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ListaPrecioQueenBeerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.listaPrecio = listaPrecio;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
