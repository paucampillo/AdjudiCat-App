import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OrganService extends HttpService{

  BASE_URL ='organ';
  BUSCAR = this.BASE_URL + '/buscar';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  buscar(nombre: any) : Observable<any> {
    return this.get(this.BUSCAR, nombre);
  }
}
