import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VsDescriptorComponent } from './vs-descriptor.component';

describe('VsDescriptorComponent', () => {
  let component: VsDescriptorComponent;
  let fixture: ComponentFixture<VsDescriptorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VsDescriptorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VsDescriptorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
