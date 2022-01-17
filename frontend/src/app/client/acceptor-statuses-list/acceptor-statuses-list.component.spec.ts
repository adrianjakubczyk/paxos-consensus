import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AcceptorStatusesListComponent } from './acceptor-statuses-list.component';

describe('AcceptorStatusesListComponent', () => {
  let component: AcceptorStatusesListComponent;
  let fixture: ComponentFixture<AcceptorStatusesListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AcceptorStatusesListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AcceptorStatusesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
