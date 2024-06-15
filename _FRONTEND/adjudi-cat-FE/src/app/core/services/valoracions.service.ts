import { Injectable } from '@angular/core';
import {HttpService} from "./http.service";
import {HttpClient} from "@angular/common/http";
import {ValoracioCustomDTO} from "../model/ValoracioCustomDTO";
import {map} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ValoracionsService extends HttpService {

  BASE_URL ='valoracio'
  VALORAR= this.BASE_URL + '/valorar';
  ELIMINAR = this.BASE_URL + '/eliminar/{idValoracio}';
  LISTAR_RECEPTOR = this.BASE_URL + '/listarPorReceptor';
  LISTAR_EMISOR = this.BASE_URL + '/listarPorEmisor';

  constructor(protected hhtpClient: HttpClient) {
    super(hhtpClient);
  }

  saveValoracio(valoracio: ValoracioCustomDTO) {
    return this.post(this.VALORAR, valoracio);
  }

  deleteValoracio(idValoracio: number) {
    return this.delete(this.ELIMINAR.replace('{idValoracio}', idValoracio.toString()));
  }

  listValoracionsReceptor(params: any) {
    return this.get(this.LISTAR_RECEPTOR, params);
  }

  listValoracionsEmisor(params: any) {
    return this.get(this.LISTAR_EMISOR, params);
  }
}
