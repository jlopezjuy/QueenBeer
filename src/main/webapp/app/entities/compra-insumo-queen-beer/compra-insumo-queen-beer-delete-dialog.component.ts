import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompraInsumoQueenBeer } from 'app/shared/model/compra-insumo-queen-beer.model';
import { CompraInsumoQueenBeerService } from './compra-insumo-queen-beer.service';

@Component({
    selector: 'jhi-compra-insumo-queen-beer-delete-dialog',
    templateUrl: './compra-insumo-queen-beer-delete-dialog.component.html'
})
export class CompraInsumoQueenBeerDeleteDialogComponent {
    compraInsumo: ICompraInsumoQueenBeer;

    constructor(
        private compraInsumoService: CompraInsumoQueenBeerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.compraInsumoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'compraInsumoListModification',
                content: 'Deleted an compraInsumo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-compra-insumo-queen-beer-delete-popup',
    template: ''
})
export class CompraInsumoQueenBeerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ compraInsumo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CompraInsumoQueenBeerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.compraInsumo = compraInsumo;
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
