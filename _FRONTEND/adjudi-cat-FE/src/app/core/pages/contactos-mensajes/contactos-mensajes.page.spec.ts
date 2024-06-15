import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ContactosMensajesPage } from './contactos-mensajes.page';
import {async} from "rxjs";

describe('ContactosMensajesPage', () => {
  let component: ContactosMensajesPage;
  let fixture: ComponentFixture<ContactosMensajesPage>;

  // @ts-ignore
  beforeEach(async(() => {
    fixture = TestBed.createComponent(ContactosMensajesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
