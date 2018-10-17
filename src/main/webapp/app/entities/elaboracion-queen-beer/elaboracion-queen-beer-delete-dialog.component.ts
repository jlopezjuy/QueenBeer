import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElaboracionQueenBeer } from 'app/shared/model/elaboracion-queen-beer.model';
import { ElaboracionQueenBeerService } from './elaboracion-queen-beer.service';

@Component({
    selector: 'jhi-elaboracion-queen-beer-delete-dialog',
    templateUrl: './elaboracion-queen-beer-delete-dialog.component.html'
})
export class ElaboracionQueenBeerDeleteDialogComponent {
    elaboracion: IElaboracionQueenBeer;

    constructor(
        private elaboracionService: ElaboracionQueenBeerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.elaboracionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'elaboracionListModification',
                content: 'Deleted an elaboracion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-elaboracion-queen-beer-delete-popup',
    template: ''
})
export class ElaboracionQueenBeerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ elaboracion }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ElaboracionQueenBeerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.elaboracion = elaboracion;
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
