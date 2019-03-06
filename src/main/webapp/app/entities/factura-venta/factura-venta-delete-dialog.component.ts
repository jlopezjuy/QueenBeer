import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFacturaVenta } from 'app/shared/model/factura-venta.model';
import { FacturaVentaService } from './factura-venta.service';

@Component({
    selector: 'jhi-factura-venta-delete-dialog',
    templateUrl: './factura-venta-delete-dialog.component.html'
})
export class FacturaVentaDeleteDialogComponent {
    facturaVenta: IFacturaVenta;

    constructor(
        protected facturaVentaService: FacturaVentaService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facturaVentaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'facturaVentaListModification',
                content: 'Deleted an facturaVenta'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-factura-venta-delete-popup',
    template: ''
})
export class FacturaVentaDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ facturaVenta }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FacturaVentaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.facturaVenta = facturaVenta;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/factura-venta', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/factura-venta', { outlets: { popup: null } }]);
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
