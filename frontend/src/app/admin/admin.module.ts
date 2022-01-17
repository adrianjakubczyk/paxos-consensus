import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { VoteFormComponent } from './vote-form/vote-form.component';
import { AcceptorListComponent } from './acceptor-list/acceptor-list.component';
import { VoteHistoryListComponent } from './vote-history-list/vote-history-list.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AdminService } from './admin.service';


@NgModule({
  declarations: [
    VoteFormComponent,
    AcceptorListComponent,
    VoteHistoryListComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    AdminService
  ]
})
export class AdminModule { }
