import {ComponentFixture, TestBed, waitForAsync} from '@angular/core/testing';
import { ChatMensajesPage } from './chat-mensajes.page';

describe('ChatMensajesPage', () => {
  let component: ChatMensajesPage;
  let fixture: ComponentFixture<ChatMensajesPage>;

  beforeEach(waitForAsync(() => {
    fixture = TestBed.createComponent(ChatMensajesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
