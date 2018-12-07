import { Component, ElementRef, OnInit } from '@angular/core';
import { JhiDataUtils, JhiLanguageService } from 'ng-jhipster';

import { Principal, AccountService, JhiLanguageHelper } from 'app/core';
import { UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from 'app/entities/user-profile';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
    selector: 'jhi-settings',
    templateUrl: './settings.component.html'
})
export class SettingsComponent implements OnInit {
    error: string;
    success: string;
    settingsAccount: any;
    languages: any[];
    userProfile: UserProfile;

    constructor(
        private account: AccountService,
        private principal: Principal,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private userProfileService: UserProfileService,
        private dataUtils: JhiDataUtils,
        private elementRef: ElementRef,
        private localStorage: LocalStorageService
    ) {}

    ngOnInit() {
        this.principal.identity().then(account => {
            this.settingsAccount = this.copyAccount(account);
            console.log(this.settingsAccount);
            console.log(this.settingsAccount.login);
            this.userProfileService.findByLogin(this.settingsAccount.login).subscribe(res => {
                console.log(res);
                this.userProfile = res.body;
                if (this.userProfile.avatar) {
                    this.localStorage.store('avatarUser', this.userProfile.avatar);
                }
            });
        });
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });
    }

    save() {
        this.account.save(this.settingsAccount).subscribe(
            () => {
                this.error = null;
                this.success = 'OK';
                this.principal.identity(true).then(account => {
                    this.settingsAccount = this.copyAccount(account);
                });
                this.languageService.getCurrent().then(current => {
                    if (this.settingsAccount.langKey !== current) {
                        this.languageService.changeLanguage(this.settingsAccount.langKey);
                    }
                });
                this.userProfileService.update(this.userProfile).subscribe(res => {
                    console.log('actualizado user profile');
                });
            },
            () => {
                this.success = null;
                this.error = 'ERROR';
            }
        );
    }

    copyAccount(account) {
        return {
            activated: account.activated,
            email: account.email,
            firstName: account.firstName,
            langKey: account.langKey,
            lastName: account.lastName,
            login: account.login,
            imageUrl: account.imageUrl
        };
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.userProfile, this.elementRef, field, fieldContentType, idInput);
    }
}
