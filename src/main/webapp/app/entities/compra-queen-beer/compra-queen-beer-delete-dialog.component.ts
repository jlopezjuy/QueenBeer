import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompraQueenBeer } from 'app/shared/model/compra-queen-beer.model';
import { CompraQueenBeerService } from './compra-queen-beer.service';

@Component({
    selector: 'jhi-compra-queen-beer-delete-dialog',
    templateUrl: './compra-queen-beer-delete-dialog.component.html'
})
export class CompraQueenBeerDeleteDialogComponent {
    compra: ICompraQueenBeer;

    constructor(private compraService: CompraQueenBeerService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.compraService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'compraListModification',
                content: 'Deleted an compra'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-compra-queen-beer-delete-popup',
    template: ''
})
export class CompraQueenBeerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compra }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompraQueenBeerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.compra = compra;
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
