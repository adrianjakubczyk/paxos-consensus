import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-vote-form',
  templateUrl: './vote-form.component.html',
  styleUrls: ['./vote-form.component.scss']
})
export class VoteFormComponent implements OnInit {
  voteName: string = '';
  voteClientId: string = '';
  constructor(
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log('weszlo');
    this.adminService.startNewVoting(this.voteName, this.voteClientId).subscribe();
  }

}
