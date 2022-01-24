import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientRoutingModule } from './client-routing.module';
import { AddVoteComponent } from './add-vote/add-vote.component';
import { AcceptorStatusesListComponent } from './acceptor-statuses-list/acceptor-statuses-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ClientService } from './client.service';


@NgModule({
  declarations: [
    AddVoteComponent,
    AcceptorStatusesListComponent
  ],
  imports: [
    CommonModule,
    ClientRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    ClientService
  ]
})
export class ClientModule { }
