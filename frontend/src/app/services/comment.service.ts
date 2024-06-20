import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl: string = 'http://localhost:8080/api/v1/comments';

  constructor(private httpClient: HttpClient) { }

  createComment(comment: any): Observable<any>{
    return this.httpClient.post<any>(this.baseUrl, comment);
  }

  deleteComment(id: number):Observable<any> {
    return this.httpClient.delete<any>(`${this.baseUrl}/${id}`);
  }
}
