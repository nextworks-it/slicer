import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VsInstanceComponent } from './vs-instance.component';

describe('VsInstanceComponent', () => {
  let component: VsInstanceComponent;
  let fixture: ComponentFixture<VsInstanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VsInstanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VsInstanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
