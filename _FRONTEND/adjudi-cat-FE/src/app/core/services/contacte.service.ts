import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class ContacteService extends HttpService {
  BASE_URL ='missatge';
  CONVERSACIONS = this.BASE_URL + '/conversacions';
  ELIMINARCONVERSA = this.BASE_URL + '/deleteConverse';
  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  findContactes(userID:any) : Observable<any> {
    return this.get(this.CONVERSACIONS+'?idUsuari='+userID);
  }

  eliminarConversa(emisorID:any, receptorID:any): Observable<any> {
    return this.delete(this.ELIMINARCONVERSA+'?idEmissor='+emisorID+'&idReceptor='+receptorID);
    //return this.delete('missatge/deleteConverse?idEmissor=17&idReceptor=14');
  }
}
