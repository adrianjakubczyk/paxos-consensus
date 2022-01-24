import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  constructor(private http: HttpClient) { }

  vote(vote: string, clientId: string) {
    return this.http.post(`http://localhost:8080/api/create-vote?vote=${vote}&clientId=${clientId}`, null)
  }
}
