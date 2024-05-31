import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../common/user';
import { UserService } from './user.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private baseUrl: string = 'http://localhost:8080/api/v1'

  constructor(private httpClient: HttpClient,
              private userService: UserService,
              private route: Router) { }

  performLogin(userData: { username: string, password: string }): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/login`, userData);
  }

  getCurrentUser(username: string): Observable<User> {
    return this.httpClient.get<User>(`${this.baseUrl}/currentUser`, { params: {username: username} });
  }

  loginAndSetCurrentUser(userData: {username: string, password: string}) {
      this.performLogin(userData).subscribe(response => {
        if (response.hasOwnProperty('token')) {
          localStorage.setItem('jwtToken', response.token);
          this.getCurrentUser(userData.username).subscribe(userResponse => {
            this.userService.setCurrentUser(userResponse);
            console.log(userResponse);
          })

          this.route.navigate(['/posts']);
        }
      });
  }
}
