import { Component, OnInit } from '@angular/core';
import { ClientService } from '../client.service';

@Component({
  selector: 'app-acceptor-statuses-list',
  templateUrl: './acceptor-statuses-list.component.html',
  styleUrls: ['./acceptor-statuses-list.component.scss']
})
export class AcceptorStatusesListComponent implements OnInit {

  acceptors: any = [];
  constructor(
    private clientService: ClientService
  ) { }

  ngOnInit(): void {
    this.getAcceptors();
  }
  getAcceptors() {
    this.clientService.getAcceptors().subscribe(
      res => this.acceptors = res
    )
  }

}
