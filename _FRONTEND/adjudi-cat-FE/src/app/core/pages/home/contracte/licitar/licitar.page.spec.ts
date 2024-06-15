import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LicitarPage } from './licitar.page';

describe('LicitarPage', () => {
  let component: LicitarPage;
  let fixture: ComponentFixture<LicitarPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(LicitarPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
