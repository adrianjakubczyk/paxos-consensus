import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AcceptorListComponent } from './acceptor-list/acceptor-list.component';
import { VoteFormComponent } from './vote-form/vote-form.component';
import { VoteHistoryListComponent } from './vote-history-list/vote-history-list.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: '',
        component: AcceptorListComponent,

      },
      {
        path: 'new-voting',
        component: VoteFormComponent
      },
      {
        path: 'vote-history',
        component: VoteHistoryListComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
