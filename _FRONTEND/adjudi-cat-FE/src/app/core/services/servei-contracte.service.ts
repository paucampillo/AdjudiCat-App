import {Injectable, Input} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {HttpService} from "./http.service";

@Injectable({
  providedIn: 'root'
})
export class ServeiContracteService extends HttpService {

  constructor(protected hhtpClient: HttpClient) {
    // @ts-ignore
    super(hhtpClient);
  }

 // private licitacionesUrl = 'licitacionesEjemplos';
  //private contratosUrl = this.licitacionesUrl + '/contratos';

/*  obtenirDades(): Observable<any[]> {
    //return this.get(this.contratosUrl,{ titulo, subtitulo,  presupuesto, fechaterminio, nombreOrganizacion,, logo });
    return this.get(this.contratosUrl,{ titulo });
  }

 */
  miMetodo(): string {
    return "Hola desde el servicio";
  }
}
