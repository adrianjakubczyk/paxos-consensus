import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  @Input() menuItems: { label: string, link: string }[] = []
  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  goTo(link: string) {
    this.router.navigate([link]);
  }

}
