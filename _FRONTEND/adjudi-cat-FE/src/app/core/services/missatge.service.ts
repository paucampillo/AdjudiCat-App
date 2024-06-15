import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class MissatgeService extends HttpService {
  BASE_URL = 'missatge';
  MISSATGES = this.BASE_URL + '/historic';
  ENVIAR = this.BASE_URL + '/enviar';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  findMensajes(userID:any, contacteID:any) :Observable<any> {
    return this.post(`${this.MISSATGES}?idEmisor=${userID}&idReceptor=${contacteID}`);
    //return this.post('missatge/historic?idEmisor=17&idReceptor=18');
  }

  eliminarMissatge(missatgeID:any) {
    return this.delete(`${this.BASE_URL}/${missatgeID}`);
  }

  enviarMissatge(idEmisor:any, idReceptor: any, mensaje:any):Observable<any> {
    return this.post(`${this.ENVIAR}?idEmisor=${idEmisor}&idReceptor=${idReceptor}&mensaje=${mensaje}`);
  }

  enviarChatbot(idEmisor: any, mensaje:any):Observable<any> {
    return this.post('chatbot/consulta?idEmissor='+idEmisor+'&mensaje='+mensaje);
  }
}
