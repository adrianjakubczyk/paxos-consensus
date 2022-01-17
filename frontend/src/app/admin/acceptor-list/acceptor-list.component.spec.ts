import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcceptorListComponent } from './acceptor-list.component';

describe('AcceptorListComponent', () => {
  let component: AcceptorListComponent;
  let fixture: ComponentFixture<AcceptorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AcceptorListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AcceptorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
