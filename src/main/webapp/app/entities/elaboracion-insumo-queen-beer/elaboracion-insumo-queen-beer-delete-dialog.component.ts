import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElaboracionInsumoQueenBeer } from 'app/shared/model/elaboracion-insumo-queen-beer.model';
import { ElaboracionInsumoQueenBeerService } from './elaboracion-insumo-queen-beer.service';

@Component({
    selector: 'jhi-elaboracion-insumo-queen-beer-delete-dialog',
    templateUrl: './elaboracion-insumo-queen-beer-delete-dialog.component.html'
})
export class ElaboracionInsumoQueenBeerDeleteDialogComponent {
    elaboracionInsumo: IElaboracionInsumoQueenBeer;

    constructor(
        private elaboracionInsumoService: ElaboracionInsumoQueenBeerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.elaboracionInsumoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'elaboracionInsumoListModification',
                content: 'Deleted an elaboracionInsumo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-elaboracion-insumo-queen-beer-delete-popup',
    template: ''
})
export class ElaboracionInsumoQueenBeerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ elaboracionInsumo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ElaboracionInsumoQueenBeerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.elaboracionInsumo = elaboracionInsumo;
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
