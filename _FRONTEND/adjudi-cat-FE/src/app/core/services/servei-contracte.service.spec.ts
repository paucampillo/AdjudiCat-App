import { TestBed } from '@angular/core/testing';

import { ServeiContracteService } from './servei-contracte.service';

describe('ServeiContracteService', () => {
  let service: ServeiContracteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServeiContracteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
