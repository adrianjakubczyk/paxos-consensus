import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin.service';

@Component({
  selector: 'app-acceptor-list',
  templateUrl: './acceptor-list.component.html',
  styleUrls: ['./acceptor-list.component.scss']
})
export class AcceptorListComponent implements OnInit {
  acceptors: any = [];
  constructor(
    private adminService: AdminService
  ) { }

  ngOnInit(): void {
    this.getAcceptors();
  }
  getAcceptors() {
    this.adminService.getAcceptors().subscribe(
      res => this.acceptors = res
    )
  }
  createError(acceptorId: number, errorType: number) {
    this.adminService.createError(acceptorId, errorType).subscribe(
      () => this.getAcceptors()
    );
  }
  deleteError(acceptorId: number) {
    this.adminService.deleteError(acceptorId).subscribe(
      () => this.getAcceptors()
    );
  }

}
