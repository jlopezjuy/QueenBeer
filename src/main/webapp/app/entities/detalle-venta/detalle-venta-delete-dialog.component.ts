import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetalleVenta } from 'app/shared/model/detalle-venta.model';
import { DetalleVentaService } from './detalle-venta.service';

@Component({
    selector: 'jhi-detalle-venta-delete-dialog',
    templateUrl: './detalle-venta-delete-dialog.component.html'
})
export class DetalleVentaDeleteDialogComponent {
    detalleVenta: IDetalleVenta;

    constructor(
        protected detalleVentaService: DetalleVentaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detalleVentaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'detalleVentaListModification',
                content: 'Deleted an detalleVenta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detalle-venta-delete-popup',
    template: ''
})
export class DetalleVentaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detalleVenta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DetalleVentaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.detalleVenta = detalleVenta;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/detalle-venta', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/detalle-venta', { outlets: { popup: null } }]);
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
