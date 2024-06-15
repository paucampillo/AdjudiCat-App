import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';
import { CanviContrasenyaPage } from './canvi-contrasenya.page';

describe('CanviContrasenyaPage', () => {
  let component: CanviContrasenyaPage;
  let fixture: ComponentFixture<CanviContrasenyaPage>;

  beforeEach(waitForAsync(() => {
    fixture = TestBed.createComponent(CanviContrasenyaPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
