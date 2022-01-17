import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  switcherText = "Admin";
  menuAdminItems: { label: string, link: string }[] = [
    { label: 'Nowe glosowanie', link: 'new-voting' },
    { label: 'Lista acceptorow', link: '' },
    { label: 'Historia głosowania', link: 'vote-history' }
  ]

  menuClientItems: { label: string, link: string }[] = [
    { label: 'Oddaj głos', link: 'client/add-vote' },
    { label: 'Status acceptorow', link: 'client/acceptor-statuses' },
    { label: 'Historia głosowania', link: 'vote-history' }
  ]

  switch() {
    if (this.switcherText == 'Admin') {
      this.switcherText = 'Klient'
    } else {
      this.switcherText = 'Admin'
    }
  }
}
