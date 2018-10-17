import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInsumoQueenBeer } from 'app/shared/model/insumo-queen-beer.model';
import { InsumoQueenBeerService } from './insumo-queen-beer.service';

@Component({
    selector: 'jhi-insumo-queen-beer-delete-dialog',
    templateUrl: './insumo-queen-beer-delete-dialog.component.html'
})
export class InsumoQueenBeerDeleteDialogComponent {
    insumo: IInsumoQueenBeer;

    constructor(private insumoService: InsumoQueenBeerService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.insumoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'insumoListModification',
                content: 'Deleted an insumo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-insumo-queen-beer-delete-popup',
    template: ''
})
export class InsumoQueenBeerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ insumo }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(InsumoQueenBeerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.insumo = insumo;
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
