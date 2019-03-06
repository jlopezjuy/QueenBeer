import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService, AccountService, Account } from 'app/core';
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
        private accountService: AccountService,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private userProfileService: UserProfileService,
        private localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.accountService.identity().then((account: Account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.accountService.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.accountService.isAuthenticated();
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
