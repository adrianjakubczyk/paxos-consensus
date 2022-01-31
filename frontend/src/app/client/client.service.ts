import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http: HttpClient) { }

  vote(vote: string, clientId: string) {
    return this.http.post(`http://localhost:8080/api/create-vote?vote=${vote}&clientId=${clientId}`, null)
  }
  getAcceptors(): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/acceptors`);
  }
}
