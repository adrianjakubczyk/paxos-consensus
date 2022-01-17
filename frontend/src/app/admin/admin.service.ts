import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  startNewVoting(voteName: string, voteClientId: string) {
    return this.http.post(`http://localhost:8080/api/create-new-vote-session?voteName=${voteName}&clientId=${voteClientId}`, null)
  }
}
