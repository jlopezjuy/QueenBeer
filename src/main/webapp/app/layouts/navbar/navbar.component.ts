import { Component, OnInit, Renderer2 } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiLanguageService } from 'ng-jhipster';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { JhiLanguageHelper, Principal, LoginModalService, LoginService } from 'app/core';
import { ProfileService } from '../profiles/profile.service';

declare const $: any;
declare const Morris: any;
declare const slimscroll: any;

@Component({
    selector: 'jhi-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['navbar.scss']
})
export class NavbarComponent implements OnInit {
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;

    previousUrl: string;

    constructor(
        private renderer: Renderer2,
        private loginService: LoginService,
        private languageService: JhiLanguageService,
        private languageHelper: JhiLanguageHelper,
        private sessionStorage: SessionStorageService,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private profileService: ProfileService,
        private router: Router,
        private localStorage: LocalStorageService
    ) {
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;

        this.router.events.subscribe(event => {
            if (event instanceof NavigationStart) {
                if (this.previousUrl) {
                    this.renderer.removeClass(document.body, this.previousUrl);
                }
                const currentUrl = event.url.split('/');
                const currentUrlSlug = currentUrl[currentUrl.length - 1];
                if (currentUrlSlug) {
                    this.renderer.addClass(document.body, currentUrlSlug);
                }
                this.previousUrl = currentUrlSlug;
            }
        });
    }

    ngOnInit() {
        this.languageHelper.getAll().then(languages => {
            this.languages = languages;
        });

        this.profileService.getProfileInfo().then(profileInfo => {
            this.inProduction = profileInfo.inProduction;
            this.swaggerEnabled = profileInfo.swaggerEnabled;
        });

        $('.theme-light-dark .t-light').on('click', function() {
            $('body').removeClass('menu_dark');
        });

        $('.theme-light-dark .t-dark').on('click', function() {
            $('body').addClass('menu_dark');
        });

        $('.m_img_btn').on('click', function() {
            $('body').toggleClass('menu_img');
        });

        $('.boxs-close').on('click', function() {
            const element = $(this);
            const cards = element.parents('.card');
            cards.addClass('closed').fadeOut();
        });
    }

    changeLanguage(languageKey: string) {
        this.sessionStorage.store('locale', languageKey);
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    logout() {
        this.collapseNavbar();
        this.loginService.logout();
        this.router.navigate(['']);
    }

    toggleNavbar() {
        this.isNavbarCollapsed = !this.isNavbarCollapsed;
    }

    getImageUrl() {
        return this.isAuthenticated() ? this.localStorage.retrieve('userAvatarProfile') : null;
    }
    getImageUserAvatarContentType() {
        return this.isAuthenticated() ? this.localStorage.retrieve('userAvatarContentType') : null;
    }
}
