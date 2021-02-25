import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlueprintDetailsComponent } from './blueprint-details.component';

describe('BlueprintDetailsComponent', () => {
  let component: BlueprintDetailsComponent;
  let fixture: ComponentFixture<BlueprintDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BlueprintDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlueprintDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
