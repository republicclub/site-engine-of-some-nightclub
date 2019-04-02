import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminEventPriceComponent } from './admin-event-price.component';

describe('AdminEventPriceComponent', () => {
  let component: AdminEventPriceComponent;
  let fixture: ComponentFixture<AdminEventPriceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdminEventPriceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminEventPriceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
