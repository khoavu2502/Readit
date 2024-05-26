import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../common/post';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private baseUrl: string = "http://localhost:8080/api/v1/posts";

  constructor(private httpClient: HttpClient) { }

  getPostList(): Observable<Post[]> {
    return this.httpClient.get<Post[]>(this.baseUrl);
  }

  getPost(id: number): Observable<Post> {
    const searchUrl = `${this.baseUrl}/${id}`;
    return this.httpClient.get<Post>(searchUrl);
  }
}
