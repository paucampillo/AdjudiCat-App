import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LicitacionsPage } from './licitacions.page';

describe('LicitacionsPage', () => {
  let component: LicitacionsPage;
  let fixture: ComponentFixture<LicitacionsPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(LicitacionsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
