import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ContractePage } from './contracte.page';
import {async} from "rxjs";

describe('ContractePage', () => {
  let component: ContractePage;
  let fixture: ComponentFixture<ContractePage>;

  // @ts-ignore
  beforeEach(async(() => {
    fixture = TestBed.createComponent(ContractePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
