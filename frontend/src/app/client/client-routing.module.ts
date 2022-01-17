import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AcceptorStatusesListComponent } from './acceptor-statuses-list/acceptor-statuses-list.component';
import { AddVoteComponent } from './add-vote/add-vote.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'add-vote',
        component: AddVoteComponent,

      },
      {
        path: 'acceptor-statuses',
        component: AcceptorStatusesListComponent
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
