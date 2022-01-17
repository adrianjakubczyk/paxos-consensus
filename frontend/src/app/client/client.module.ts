import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { AddVoteComponent } from './add-vote/add-vote.component';
import { AcceptorStatusesListComponent } from './acceptor-statuses-list/acceptor-statuses-list.component';


@NgModule({
  declarations: [
    AddVoteComponent,
    AcceptorStatusesListComponent
  ],
  imports: [
    CommonModule,
    ClientRoutingModule
  ]
})
export class ClientModule { }
