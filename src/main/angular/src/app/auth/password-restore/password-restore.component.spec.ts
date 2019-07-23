import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordRestoreComponent } from './password-restore.component';

describe('PasswordRestoreComponent', () => {
  let component: PasswordRestoreComponent;
  let fixture: ComponentFixture<PasswordRestoreComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PasswordRestoreComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordRestoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
