import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VsInstanceDetailsComponent } from './vs-instance-details.component';

describe('VsInstanceDetailsComponent', () => {
  let component: VsInstanceDetailsComponent;
  let fixture: ComponentFixture<VsInstanceDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VsInstanceDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VsInstanceDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
