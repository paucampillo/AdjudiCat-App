import { Injectable } from '@angular/core';
import { apiKeys } from 'src/environments/api-keys';
import { UsuariService } from '../services/usuari.service'; // Importa el servicio de usuario


declare var google: any;

declare global {
  interface Window { mapInit: any; }
}

@Injectable({
  providedIn: 'root'
})
export class GooglemapsService {

  apiKey = apiKeys.ApiKeyGoogleMaps; //enviorment.ApiKeyGoogleMaps
  mapsLoaded = false;

  constructor(private usuariServei: UsuariService) { }

  init(renderer: any, document: any): Promise<any> {
    return new Promise((resolve) => {

      if (this.mapsLoaded) {
        resolve(true);
        return;
      }

      const script = renderer.createElement('script');
      script.id = 'googleMaps';
      script.async = true;
      script.defer = true;

      window.mapInit = () => {
        this.mapsLoaded = true;
        if (google) {
        } else {
        }
        resolve(true);
        return;
      }

      const idUsuariStr = localStorage.getItem('idUsuari');
      if (idUsuariStr !== null) {
        const idUsuari = parseInt(idUsuariStr, 10);
        this.usuariServei.getUsuari(idUsuari).subscribe(data => {
          const usuari = data;
          let lang = usuari.idioma?.codi;

          if (lang === 'CAST') {
            lang = 'es';
          } else if (lang === 'ENG') {
            lang = 'en';
          } else {
            lang = 'ca';
          }

          if (this.apiKey) {
            script.src = 'https://maps.googleapis.com/maps/api/js?key=' + this.apiKey + '&callback=mapInit&language='+lang;
            //script.src = 'https://maps.googleapis.com/maps/api/js?key=' + this.apiKey + '&libraries=places&callback=initMap';
          } else {
            script.src = 'https://maps.googleapis.com/maps/api/js?callback=mapInit&language='+lang;
          }

          renderer.appendChild(document.body, script);
        });
      }
    });
  }
}
