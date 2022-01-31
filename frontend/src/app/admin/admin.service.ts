import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IVote } from './vote-history-list/vote-history-list.component';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  startNewVoting(voteName: string, voteClientId: string) {
    return this.http.post(`http://localhost:8080/api/create-new-vote-session?voteName=${voteName}&clientId=${voteClientId}`, null)
  }

  getVoteHistory(): Observable<IVote[]> {
    return this.http.get<IVote[]>(`http://localhost:8080/api/votes`);
  }


  getAcceptors(): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/acceptors`);
  }

  createError(acceptorId: number, errorType: number) {
    return this.http.post(`http://localhost:8080/api/create-error?acceptorId=${acceptorId}&errorType=${errorType}`, null)
  }

  deleteError(acceptorId: number) {
    return this.http.post(`http://localhost:8080/api/delete-error?acceptorId=${acceptorId}`, null)
  }
}
