import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export abstract class HttpService {

  private baseUrl = 'nattech.fib.upc.edu:40390/api/v1/adj/';

  protected constructor(protected httpClient: HttpClient) { }

  get(endpoint: string, params?: HttpParams): Observable<any> {
    return this.httpClient.get(this.baseUrl + endpoint, {params});
  }

  delete(endpoint: string, params = {}): Observable<any> {
    return this.httpClient.delete(this.baseUrl + endpoint)
      .pipe(catchError(this.handleError));
  }

  post(endpoint: string, params: Object = {}, queryParams?: HttpParams): Observable<any> {
    return this.httpClient.post(this.baseUrl + endpoint, params, {
      params: queryParams
    })
      .pipe(catchError(this.handleError));
  }

  put(endpoint: string, params: Object, queryParams?: HttpParams): Observable<any> {
    return this.httpClient.put(this.baseUrl + endpoint, params, {
      params: queryParams
    })
      .pipe(catchError(this.handleError));
  }

  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client - side error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // server - side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    if (error.status != 409) console.log(errorMessage);

    return throwError({
      code: error.status,
      message: errorMessage
    });
  }
}
