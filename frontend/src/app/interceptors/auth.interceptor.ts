import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const toastr = inject(ToastrService);
  const router = inject(Router);
  const token = localStorage.getItem('jwtToken');

  let authRequest = req;

  if (token) {
    authRequest = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${token}`)
    });
  }

  return next(authRequest).pipe(
    catchError((error) => {
      switch(error.status) {
        case 401: {
            router.navigate(['/login']);
            toastr.error('You have to login to perform this action');
            break;
          }

        case 403: {
          router.navigate(['/error/403']);
          break;
        }

        case 404: {
          router.navigate(['/error/404']);
          break;
        }
      }
      return throwError(() => error);
    })
  );
};
