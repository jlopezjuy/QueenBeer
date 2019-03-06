import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEnvase } from 'app/shared/model/envase.model';

@Component({
    selector: 'jhi-envase-detail',
    templateUrl: './envase-detail.component.html'
})
export class EnvaseDetailComponent implements OnInit {
    envase: IEnvase;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ envase }) => {
            this.envase = envase;
        });
    }

    previousState() {
        window.history.back();
    }
}
