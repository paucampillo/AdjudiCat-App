import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CompteGooglePage } from './compte-google.page';

describe('CompteGooglePage', () => {
  let component: CompteGooglePage;
  let fixture: ComponentFixture<CompteGooglePage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(CompteGooglePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
