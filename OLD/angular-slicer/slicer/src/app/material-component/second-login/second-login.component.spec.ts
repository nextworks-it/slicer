import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecondLoginComponent } from './second-login.component';

describe('SecondLoginComponent', () => {
  let component: SecondLoginComponent;
  let fixture: ComponentFixture<SecondLoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecondLoginComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecondLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
