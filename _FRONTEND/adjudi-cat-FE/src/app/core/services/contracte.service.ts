import { Injectable } from '@angular/core';
import * as http from "http";
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {FiltrarContracte} from "../model/FiltrarContracte";
import {Contracte} from "../model/contracte";

@Injectable({
  providedIn: 'root'
})
export class ContracteService extends HttpService{

  BASE_URL ='contracte';
  HISTORIC = this.BASE_URL + '/historic';
  PAGINACIO= this.BASE_URL + '/paginacio'
  PUBLICAR = this.BASE_URL + '/publicar';
  CREATS = this.BASE_URL + '/byCreador';
  CREATSHISTORIC = this.BASE_URL + '/historicByCreador';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  findContracteId(idContracte: number, idUser: number):Observable<any> {
    return this.get(this.BASE_URL + `/${idContracte}` + `/${idUser}`);
  }

  findPaginated(filtre: FiltrarContracte, params: any) : Observable<any> {
    return this.post(this.PAGINACIO,  filtre, params);
  }

  findPaginatedAndSorted(filtre: FiltrarContracte, params: any) : Observable<any> {
    return this.post(this.PAGINACIO,  filtre, params);
  }

  findPaginatedHistoric(filtre: FiltrarContracte) : Observable<any> {
    return this.post(this.HISTORIC,  filtre);
  }

  eliminar(idContracte: number) : Observable<any> {
    return this.delete(this.BASE_URL + `/${idContracte}`);
  }

  publicar(contracte: Contracte) : Observable<any> {
    return this.post(this.PUBLICAR, contracte);
  }

  findPaginatedCreador(idUsuari: any) {
    return this.post(this.CREATS, {}, idUsuari);
  }

  findPaginatedHistoricCreador(idUsuari: any) {
    return this.post(this.CREATSHISTORIC, {}, idUsuari);
  }
}
