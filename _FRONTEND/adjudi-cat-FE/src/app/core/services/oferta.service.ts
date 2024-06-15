import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import { Plugins } from '@capacitor/core';
import {Licitar} from "../model/licitar";

const { Storage } = Plugins;


@Injectable({
  providedIn: 'root'
})
export class OfertaService extends HttpService {

  BASE_URL ='oferta'
  LICITAR= this.BASE_URL + '/licitar';
  HISTORIC = this.BASE_URL + '/historicOfertes';
  OBERTES = this.BASE_URL + '/ofertesByUsuari';
  TANCADES = this.BASE_URL + '/historicOfertesByUsuari';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  ultimesLicitacions(idContracte: number) {
    return this.get(this.HISTORIC + `/${idContracte}`);
  }

  licitar(valor: Licitar): Observable<any> {
    return this.post(this.LICITAR,  valor ); // Suponiendo que estás enviando el valor a través de una solicitud POST
  }

  findOfertesByUsuari(params: any) {
    return this.post(this.OBERTES, {}, params);
  }

  findHistoricOfertesByUsuari(params: any) {
    return this.post(this.TANCADES, {}, params);
  }
}
