import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteHistoryListComponent } from './vote-history-list.component';

describe('VoteHistoryListComponent', () => {
  let component: VoteHistoryListComponent;
  let fixture: ComponentFixture<VoteHistoryListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteHistoryListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoteHistoryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
