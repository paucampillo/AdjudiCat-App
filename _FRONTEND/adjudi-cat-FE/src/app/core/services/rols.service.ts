import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RolsService extends HttpService{

  BASE_URL ='rols';
  LISTAR = this.BASE_URL + '/listar';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  listar() : Observable<any> {
    return this.get(this.LISTAR);
  }
}
