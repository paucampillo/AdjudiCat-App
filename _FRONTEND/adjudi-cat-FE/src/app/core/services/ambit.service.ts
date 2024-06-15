import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AmbitService extends HttpService{

  private BASE_URL = 'ambit';
  private LISTAR = this.BASE_URL + '/listar';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  listar() : Observable<any> {
    return this.get(this.LISTAR);
  }
}
