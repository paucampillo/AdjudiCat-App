import { ComponentFixture, TestBed } from '@angular/core/testing';
import { GestioUsuarisPage } from './gestio-usuaris.page';

describe('GestioUsuarisPage', () => {
  let component: GestioUsuarisPage;
  let fixture: ComponentFixture<GestioUsuarisPage>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(GestioUsuarisPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
