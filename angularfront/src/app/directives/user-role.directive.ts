import { Directive, ViewContainerRef, Input, TemplateRef} from '@angular/core';
import { TokenStorageService } from '../services/token-storage.service';

@Directive({
  selector: '[appUserRole]'
})
export class UserRoleDirective {

  userRole: string;

  constructor(private tokenStorage: TokenStorageService,
      private templateRef: TemplateRef<any>,
      private viewContainer: ViewContainerRef) { }

  ngOnInit() {
    let hasAccess = false;

    if (this.tokenStorage.isTokenExist() && this.userRole) {
        hasAccess = this.tokenStorage.hasRole(this.userRole);
    }

    if (hasAccess) {
        this.viewContainer.createEmbeddedView(this.templateRef);
    } else {
        this.viewContainer.clear();
    }
}

  @Input()
  set appUserRole(role: string) {
      this.userRole = role;
  }

}
