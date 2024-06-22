import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SecurityService } from '../services/security.service';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const securityService = inject(SecurityService);
  const toastr = inject(ToastrService);

  if (securityService.isLoggedIn()) {
    router.navigate(['/posts']);
    toastr.info("You have to logout before perform this action");
    return false;
  } else {
    return true;
  }
};
