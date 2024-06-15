import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService extends HttpService{

  private BASE_URL = 'usuari';
  public LOGIN = this.BASE_URL + '/login';
  public GOOGLE_LOGIN = this.BASE_URL + '/loginGoogle';
  private readonly TOKEN_LOCAL = 'token';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  signInWithGoogle(requestData: any): Observable<any> {
    return this.post(this.GOOGLE_LOGIN, {}, requestData);
  }

  iniciarSesio(requestData: any): Observable<any> {
    return this.post(this.LOGIN, {}, requestData);

  }

  get existsToken(): boolean {
    return localStorage.getItem(this.TOKEN_LOCAL) != null;
  }

  get getToken(): string {
    const token = localStorage.getItem(this.TOKEN_LOCAL);
    return token ? token : '';
  }
}
