import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OrderMenuFoodComponent } from './order-menu-food.component';

describe('OrderMenuFoodComponent', () => {
  let component: OrderMenuFoodComponent;
  let fixture: ComponentFixture<OrderMenuFoodComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OrderMenuFoodComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrderMenuFoodComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
