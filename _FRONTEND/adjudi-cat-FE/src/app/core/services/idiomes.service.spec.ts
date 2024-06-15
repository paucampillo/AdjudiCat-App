import { TestBed } from '@angular/core/testing';

import { IdiomesService } from './idiomes.service';

describe('IdiomesService', () => {
  let service: IdiomesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IdiomesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
