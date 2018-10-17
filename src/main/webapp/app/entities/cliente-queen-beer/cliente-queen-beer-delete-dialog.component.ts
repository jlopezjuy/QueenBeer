import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClienteQueenBeer } from 'app/shared/model/cliente-queen-beer.model';
import { ClienteQueenBeerService } from './cliente-queen-beer.service';

@Component({
    selector: 'jhi-cliente-queen-beer-delete-dialog',
    templateUrl: './cliente-queen-beer-delete-dialog.component.html'
})
export class ClienteQueenBeerDeleteDialogComponent {
    cliente: IClienteQueenBeer;

    constructor(
        private clienteService: ClienteQueenBeerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.clienteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'clienteListModification',
                content: 'Deleted an cliente'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cliente-queen-beer-delete-popup',
    template: ''
})
export class ClienteQueenBeerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cliente }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ClienteQueenBeerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.cliente = cliente;
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
