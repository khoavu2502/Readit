import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
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

  createPost(postData: any): Observable<Post> {
    console.log(postData);

    return this.httpClient.post<Post>(`${this.baseUrl}`, postData).pipe(
      catchError((err) => {
        return throwError(() => err);
      }
    ))
  }
}
