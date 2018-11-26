import { Component, OnInit, Renderer2 } from '@angular/core';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationStart } from '@angular/router';

import { JhiLanguageHelper, LoginModalService, LoginService, Principal } from 'app/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { VERSION } from 'app/app.constants';
import { SessionStorageService } from 'ngx-webstorage';
import { JhiLanguageService } from 'ng-jhipster';

declare const $: any;
declare const jquery: any;
declare const screenfull: any;

@Component({
    selector: 'jhi-main',
    templateUrl: './main.component.html'
})
export class JhiMainComponent implements OnInit {
    previousUrl: string;
    inProduction: boolean;
    isNavbarCollapsed: boolean;
    languages: any[];
    swaggerEnabled: boolean;
    modalRef: NgbModalRef;
    version: string;

    constructor(
        private jhiLanguageHelper: JhiLanguageHelper,
        private router: Router,
        private renderer: Renderer2,
        private loginService: LoginService,
        private principal: Principal,
        private sessionStorage: SessionStorageService,
        private languageService: JhiLanguageService,
        private loginModalService: LoginModalService
    ) {
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
        this.version = VERSION ? 'v' + VERSION : '';
        this.isNavbarCollapsed = true;
    }

    private getPageTitle(routeSnapshot: ActivatedRouteSnapshot) {
        let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : 'queenBeerApp';
        if (routeSnapshot.firstChild) {
            title = this.getPageTitle(routeSnapshot.firstChild) || title;
        }
        return title;
    }

    ngOnInit() {
        this.router.events.subscribe(event => {
            if (event instanceof NavigationEnd) {
                this.jhiLanguageHelper.updateTitle(this.getPageTitle(this.router.routerState.snapshot.root));
            }
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
        this.jhiLanguageHelper.getAll().then(languages => {
            this.languages = languages;
        });
        this.swaggerEnabled = true;
    }

    changeLanguage(languageKey: string) {
        this.sessionStorage.store('locale', languageKey);
        this.languageService.changeLanguage(languageKey);
    }

    collapseNavbar() {
        this.isNavbarCollapsed = true;
    }

    isAuthenticated() {
        console.log('autenticado si o no');
        console.log(this.principal.isAuthenticated());
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
        return this.isAuthenticated() ? this.principal.getImageUrl() : null;
    }
}
