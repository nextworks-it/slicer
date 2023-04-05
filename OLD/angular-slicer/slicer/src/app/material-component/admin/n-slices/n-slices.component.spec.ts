import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NSlicesComponent } from './n-slices.component';

describe('NSlicesComponent', () => {
  let component: NSlicesComponent;
  let fixture: ComponentFixture<NSlicesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NSlicesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NSlicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
