import { TestBed } from '@angular/core/testing';

import { ContracteService } from './contracte.service';

describe('ContractesServicioService', () => {
  let service: ContracteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContracteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
