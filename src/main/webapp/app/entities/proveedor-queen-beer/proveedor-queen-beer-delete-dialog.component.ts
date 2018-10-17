import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProveedorQueenBeer } from 'app/shared/model/proveedor-queen-beer.model';
import { ProveedorQueenBeerService } from './proveedor-queen-beer.service';

@Component({
    selector: 'jhi-proveedor-queen-beer-delete-dialog',
    templateUrl: './proveedor-queen-beer-delete-dialog.component.html'
})
export class ProveedorQueenBeerDeleteDialogComponent {
    proveedor: IProveedorQueenBeer;

    constructor(
        private proveedorService: ProveedorQueenBeerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.proveedorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'proveedorListModification',
                content: 'Deleted an proveedor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-proveedor-queen-beer-delete-popup',
    template: ''
})
export class ProveedorQueenBeerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ proveedor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProveedorQueenBeerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.proveedor = proveedor;
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
