import { Injectable } from '@angular/core';
import { User } from '../common/user';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  constructor(private route: Router) { 
  }

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
    this.route.navigate(['/']);
  }
}
