import { TestBed } from '@angular/core/testing';
import { MissatgeService } from './missatge.service';

describe('MissatgeService', () => {
  let service: MissatgeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MissatgeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
