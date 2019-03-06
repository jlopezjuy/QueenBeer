import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductoEnvase } from 'app/shared/model/producto-envase.model';
import { ProductoEnvaseService } from './producto-envase.service';

@Component({
    selector: 'jhi-producto-envase-delete-dialog',
    templateUrl: './producto-envase-delete-dialog.component.html'
})
export class ProductoEnvaseDeleteDialogComponent {
    productoEnvase: IProductoEnvase;

    constructor(
        protected productoEnvaseService: ProductoEnvaseService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.productoEnvaseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'productoEnvaseListModification',
                content: 'Deleted an productoEnvase'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-producto-envase-delete-popup',
    template: ''
})
export class ProductoEnvaseDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ productoEnvase }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProductoEnvaseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.productoEnvase = productoEnvase;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/producto-envase', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/producto-envase', { outlets: { popup: null } }]);
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
