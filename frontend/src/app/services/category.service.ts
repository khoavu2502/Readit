import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../common/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl: string = 'http://localhost:8080/api/v1/categories';

  constructor(private httpClient: HttpClient) { }

  loadCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(`${this.baseUrl}`);
  }
}
