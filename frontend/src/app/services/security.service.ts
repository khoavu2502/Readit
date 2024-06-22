import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, catchError, throwError } from 'rxjs';
import { User } from '../common/user';
import { UserService } from './user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private baseUrl: string = 'http://localhost:8080/api/v1'
  private isErrorSubject = new Subject<boolean>();
  isError$ = this.isErrorSubject.asObservable();
  
  constructor(private httpClient: HttpClient,
              private userService: UserService,
              private route: Router,
              private toastr: ToastrService) { }

  performLogin(userData: { username: string, password: string }): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/login`, userData);
  }

  performSignup(userData: { username: string,
                            email: string,
                            password: string,
                            passwordConfirmation: string
                          }): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/register`, userData).pipe(
      catchError((error) => {
        return throwError(() => error);
      })
    );
  }

  getCurrentUser(): Observable<User> {
    return this.httpClient.get<User>(`${this.baseUrl}/currentUser`);
  }

  loginAndSetCurrentUser(userData: {username: string, password: string}) {
    this.performLogin(userData).subscribe({
      next: (data) => {
        this.isErrorSubject.next(false);
        localStorage.setItem('jwtToken', data.token);
        localStorage.setItem('expiresIn', data.expiresIn);

        this.getCurrentUser().subscribe(userResponse => {
          this.userService.setCurrentUser(userResponse);
        })

        this.showSuccess();
        this.route.navigate(['/posts']);
      },

      error: (errors) => {
        this.isErrorSubject.next(true);
      }
    })
  }

  isLoggedIn():boolean {
    const token = localStorage.getItem('jwtToken');
    return !!token;
  }

  showSuccess() {
    this.toastr.success('You have succesfully login');
  }
}
