import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';

export interface IVote {
  presentVote: string;
  presentVotes: Array<string>;
  voteResult: string;
}
@Component({
  selector: 'app-vote-history-list',
  templateUrl: './vote-history-list.component.html',
  styleUrls: ['./vote-history-list.component.scss']
})
export class VoteHistoryListComponent implements OnInit {
  votes: IVote[] = []
  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.adminService.getVoteHistory().subscribe(
      res => this.votes = res
    )
  }

}
