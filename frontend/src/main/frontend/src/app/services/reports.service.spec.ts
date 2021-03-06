import { TestBed } from '@angular/core/testing';

import { ReportsService } from './reports.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RequestDate } from '../models/requestDate';

describe('ReportsService', () => {
  let reportsService: ReportsService;
  let httpMock: HttpTestingController;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ]
    });

  reportsService = TestBed.get(ReportsService);
  httpMock = TestBed.get(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  fit('should be created', () => {
    const service: ReportsService = TestBed.get(ReportsService);
    expect(service).toBeTruthy();
  });

  fit('should return a report', () => {
    const requestDate = new RequestDate(new Date(2018, 1, 1), new Date(2018, 5, 5))

    reportsService.getReports(requestDate)
      .subscribe((response : any) => {
        expect(response.toBeTruthy());
      });
    const httpRequest = httpMock.expectOne('http://localhost:8080/api/reports/get_total');
    expect(httpRequest.request.method).toEqual('POST');
    //httpRequest.flush(requestDate);
  });
});
