import { Component, OnInit } from '@angular/core';
import { ClientService } from '../client.service';

@Component({
  selector: 'app-add-vote',
  templateUrl: './add-vote.component.html',
  styleUrls: ['./add-vote.component.scss']
})
export class AddVoteComponent implements OnInit {
  vote: string = '';
  clientId: string = '';
  constructor(
    private clientService: ClientService
  ) { }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log('weszlo');
    this.clientService.vote(this.vote, this.clientId).subscribe();
  }

}
