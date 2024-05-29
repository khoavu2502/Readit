import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  private baseUrl: string = 'http://localhost:8080/api/v1'


  constructor(private httpClient: HttpClient) { }

  performLogin(userData: { username: string, password: string }): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/login`, userData);
  }
}
