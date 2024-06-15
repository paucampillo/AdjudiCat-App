import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Usuari} from "../model/usuari";
import {CanviContrasenya} from "../model/canvi-contrasenya";

@Injectable({
  providedIn: 'root'
})
export class UsuariService extends HttpService{

  BASE_URL = 'usuari';
  EDIT = this.BASE_URL + '/edit';
  CONTRASENYA = this.BASE_URL + '/password';
  REGISTER = this.BASE_URL + '/register';
  GETALL = this.BASE_URL + '/allUsers';
  DESBLOQUEAR = this.BASE_URL + '/desbloquear';
  BLOQUEAR = this.BASE_URL + '/bloquear';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  getUsuari(idUsuari: any) : Observable<any> {
    return this.get(this.BASE_URL + `/${idUsuari}`);
  }

  edit(usuari: Usuari) : Observable<any> {
    return this.put(this.EDIT, usuari);
  }

  canviContrasenya(canviContrasenya: CanviContrasenya) : Observable<any> {
    return this.put(this.CONTRASENYA, canviContrasenya);
  }

  register(usuari: Usuari, isGoogleUser: any) : Observable<any> {
    return this.post(this.REGISTER, usuari, isGoogleUser);
  }

  findAllUsuaris(){
    return this.get(this.GETALL);
  }

  desbloquejarUsuari(usuariID: any) : Observable<any> {
    return this.put(this.DESBLOQUEAR+'?idUser='+usuariID, {});
  }

  bloquejarUsuari(usuariID: any) : Observable<any> {
    return this.put(this.BLOQUEAR+'?idUser='+usuariID, {});
  }
}
