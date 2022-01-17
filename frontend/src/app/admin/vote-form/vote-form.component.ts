import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-vote-form',
  templateUrl: './vote-form.component.html',
  styleUrls: ['./vote-form.component.scss']
})
export class VoteFormComponent implements OnInit {
  voteName: string = '';
  voteClientId: string = '';
  constructor() { }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log('weszlo')
  }

}
