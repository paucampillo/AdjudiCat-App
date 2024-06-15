import { TestBed } from '@angular/core/testing';

import { ValoracionsService } from './valoracions.service';

describe('ValoracionsService', () => {
  let service: ValoracionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValoracionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
