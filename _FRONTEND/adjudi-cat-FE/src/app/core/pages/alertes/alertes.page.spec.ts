import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AlertesPage } from './alertes.page';
import {async} from "rxjs";

describe('AlertesPage', () => {
  let component: AlertesPage;
  let fixture: ComponentFixture<AlertesPage>;

  // @ts-ignore
  beforeEach(async(() => {
    fixture = TestBed.createComponent(AlertesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
