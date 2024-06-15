import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TipusLicitacionsPage } from './tipus-licitacions.page';

describe('TipusLicitacionsPage', () => {
  let component: TipusLicitacionsPage;
  let fixture: ComponentFixture<TipusLicitacionsPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TipusLicitacionsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
