import { Injectable } from '@angular/core';
import { apiKeys } from 'src/environments/api-keys';

declare var google: any;

declare global {
  interface Window { calendarInit: any; }
}

@Injectable({
  providedIn: 'root'
})
export class GooglecalendarService {

  apiKey = apiKeys.ApiKeyGoogleCalendar;
  calendarLoaded = false;

  constructor() { }

  init(renderer: any, document: any): Promise<any> {
    return new Promise((resolve) => {

      if (this.calendarLoaded) {
        resolve(true);
        return;
      }

      const script = renderer.createElement('script');
      script.id = 'googleCalendar';
      script.async = true;
      script.defer = true;

      window.calendarInit = () => {
        this.calendarLoaded = true;
        if (google) {
        } else {
        }
        resolve(true);
        return;
      }

      if (this.apiKey) {
        script.src = 'https://apis.google.com/js/api.js?key=' + this.apiKey + '&callback=calendarInit';
      } else {
        script.src = 'https://apis.google.com/js/api.js?callback=calendarInit';
      }

      renderer.appendChild(document.body, script);
    });
  }
}
