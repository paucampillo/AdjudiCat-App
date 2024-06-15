import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {HttpService} from "./http.service";
import {ReturnFavoritsDTO} from "../model/ReturnFavoritsDTO";

@Injectable({
  providedIn: 'root'
})
export class FavoritsService extends HttpService {

  BASE_URL ='favorits';
  ADD = this.BASE_URL + '/add';
  DELETE = this.BASE_URL + '/delete';
  LISTAR = this.BASE_URL + '/list';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  addFavorit(fav: ReturnFavoritsDTO) {
    return this.post(this.ADD, fav);
  }

  removeFavorit(fav: ReturnFavoritsDTO) {
    return this.post(this.DELETE, fav);
  }

  listarFavorits(params: any) {
    return this.post(this.LISTAR, {}, params);
  }
}
