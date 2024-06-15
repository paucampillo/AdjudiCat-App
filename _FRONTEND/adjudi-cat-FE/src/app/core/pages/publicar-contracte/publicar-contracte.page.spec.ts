import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PublicarContractePage } from './publicar-contracte.page';
import {async} from "rxjs";

describe('PublicarContractePage', () => {
  let component: PublicarContractePage;
  let fixture: ComponentFixture<PublicarContractePage>;

  // @ts-ignore
  beforeEach(async(() => {
    fixture = TestBed.createComponent(PublicarContractePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
