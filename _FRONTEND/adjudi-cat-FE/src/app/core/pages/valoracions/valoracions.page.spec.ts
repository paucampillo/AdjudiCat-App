import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ValoracionsPage } from './valoracions.page';

describe('ValoracionsPage', () => {
  let component: ValoracionsPage;
  let fixture: ComponentFixture<ValoracionsPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(ValoracionsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
