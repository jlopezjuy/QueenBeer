import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, Principal, Account } from 'app/core';
import { UserProfileService } from 'app/entities/user-profile';
import { LocalStorageService } from 'ngx-webstorage';
import { UserProfile } from 'app/shared/model/user-profile.model';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private userProfileService: UserProfileService,
        private localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
            this.loadUserProfileAvatar(this.account.login);
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private loadUserProfileAvatar(login: string) {
        let userProfile: UserProfile;
        this.userProfileService.findByLogin(login).subscribe(resp => {
            userProfile = resp.body;
            this.localStorage.store('userAvatarProfile', userProfile.avatar);
            this.localStorage.store('userAvatarContentType', userProfile.avatarContentType);
        });
    }
}
