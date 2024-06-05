import { Injectable } from '@angular/core';
import { User } from '../common/user';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  private baseUrl: string = 'http://localhost:8080/api/v1/users'

  constructor(private route: Router,
              private httpClient: HttpClient) { }

  setCurrentUser(user: User | null) {
    this.currentUserSubject.next(user);

    localStorage.setItem('currentUser', JSON.stringify(user));
  }

  loadCurrentUser() {
    const user = localStorage.getItem('currentUser');
    if (user) {
      this.currentUserSubject.next(JSON.parse(user));
    } else {
      this.currentUserSubject.next(null);
    }
  }

  clearCurrentUser() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  getUser(id: number): Observable<User> {
    return this.httpClient.get<User>(`${this.baseUrl}/${id}`);
  }

  follow(followerId: any, followingId: any): Observable<any> {
    return this.httpClient.post<any>(`${this.baseUrl}/${followerId}/follow/${followingId}`, null, {headers: {skip: 'true'}});
  }

  unFollow(followerId: any, followingId: any): Observable<any> {
    return this.httpClient.post<any>(`${this.baseUrl}/${followerId}/unfollow/${followingId}`, null, {headers: {skip: 'true'}});
  }
}
