import { Directive, TemplateRef,  ViewContainerRef, OnInit } from '@angular/core';
import { TokenStorageService } from '../services/token-storage.service';

@Directive({
  selector: '[appUserLogged]'
})
export class UserLoggedDirective implements OnInit {

  constructor(private templateRef: TemplateRef<any>,
    private tokenStorage: TokenStorageService,
    private viewContainer: ViewContainerRef) { }

  ngOnInit() {
    const hasAccess = this.tokenStorage.isTokenExist();
    if (hasAccess) {
        this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
        this.viewContainer.clear();
    }
  }
}
