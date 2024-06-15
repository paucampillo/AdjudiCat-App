import { Component, Input, OnInit } from '@angular/core';
import { apiKeys } from '../../../environments/api-keys';
import { credentials } from '../../../environments/credentials-googleAuth';
import {IonicModule, LoadingController, ModalController} from '@ionic/angular';
import { FormsModule, ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { addIcons } from "ionicons";
import { logoGoogle, refreshOutline, exitOutline } from "ionicons/icons";
import { HeaderGenericoComponent } from "../pages/ComponentesCabecera/header-generico/header-generico.component";
import { MenuComponent } from "../pages/menu/menu.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../services/usuari.service";

declare var gapi: any;
declare var google: any;

@Component({
  selector: 'app-googlecalendar',
  templateUrl: 'googlecalendar.component.html',
  styleUrls: ['googlecalendar.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    HeaderGenericoComponent,
    MenuComponent,
    TranslateModule
  ]
})
export class GooglecalendarComponent implements OnInit {
  private CLIENT_ID = credentials.clientId;
  private API_KEY = apiKeys.ApiKeyGoogleCalendar;
  private DISCOVERY_DOC = 'https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest';
  private SCOPES = 'https://www.googleapis.com/auth/calendar';
  private tokenClient: any;
  private gapiInited = false;
  private gisInited = false;
  private calendarId: string = 'primary'; // Default to primary
  @Input() selectedDate!: string;
  events: any[] = [];
  eventForm: FormGroup;
  isUserLoggedIn = localStorage.getItem('googleCalendarToken') !== null;
  private idUsuari: number;
  private usuari: any;
  titulo: string;
  private lang: string;
  private message: string;
  private actualitza: any;
  private autorizeText: string;
  private actualitzaText: string;


  constructor(public modalController: ModalController, private loadingController: LoadingController, private usuariServei: UsuariService, private translate: TranslateService) {
    this.eventForm = new FormGroup({
      summary: new FormControl('', Validators.required),
      location: new FormControl(''),
      description: new FormControl(''),
      startDate: new FormControl('', Validators.required),
      endDate: new FormControl('', Validators.required),
      startTime: new FormControl('', Validators.required),
      endTime: new FormControl('', Validators.required),
      startZone: new FormControl('Europe/Madrid'),
      endZone: new FormControl('Europe/Madrid'),
    });
    addIcons({ logoGoogle });
    addIcons({ refreshOutline });
    addIcons({ exitOutline });
  }

  async ngOnInit() {
    this.loadUsuari();
    const loading = await this.presentLoading();
    try {
      this.loadGapi();
      this.loadGis();
    } finally {
      await loading.dismiss();
    }
  }

  loadUsuari() {
    const idUsuariStr = localStorage.getItem('idUsuari');
    if (idUsuariStr !== null) {
      this.idUsuari = parseInt(idUsuariStr, 10);
      this.usuariServei.getUsuari(this.idUsuari).subscribe(data => {
        this.usuari = data;
        if (this.usuari.idioma?.codi == 'ENG') {
          this.translate.use('en');
          this.lang = 'en';
        } else if (this.usuari.idioma?.codi == 'CAST') {
          this.translate.use('es');
          this.lang = 'es';
        } else {
          this.translate.use('ca');
          this.lang = 'ca';
        }
        this.translate.get('ALERTES.TITOL').subscribe((res: string) => {
          this.titulo = res;
        });
        this.translate.get('ALERTES.MESSAGE').subscribe((res: string) => {
          this.message = res;
        });
        this.translate.get('ALERTES.ACTUALITZA').subscribe((res: string) => {
          this.actualitza = res;
        });
        this.translate.get('ALERTES.ACTUALITZA').subscribe((res: string) => {
          this.actualitzaText = `<ion-icon name="refresh-outline" slot="icon-only"></ion-icon> ${res}`;
        });
        this.translate.get('ALERTES.AUTORITZA').subscribe((res: string) => {
          this.autorizeText = `<ion-icon name="logo-google" slot="icon-only"></ion-icon> ${res}`;
        });

        this.updateCalendarIframe();
      });
    }
  }

  async presentLoading() {
    const loading = await this.loadingController.create({
      message: this.message,
    });
    await loading.present();
    return loading;
  }

  onIframeLoad() {
    const spinner = document.getElementById('iframe-spinner');
    if (spinner) {
      spinner.style.display = 'none';
    }
  }


  loadGapi() {
    if (!document.getElementById('gapi-script')) {
      const script = document.createElement('script');
      script.src = 'https://apis.google.com/js/api.js';
      script.id = 'gapi-script';
      script.onload = () => this.gapiLoaded();
      document.body.appendChild(script);
    } else {
      this.gapiLoaded();
    }
  }

  loadGis() {
    if (!document.getElementById('gis-script')) {
      const script = document.createElement('script');
      script.src = 'https://accounts.google.com/gsi/client';
      script.id = 'gis-script';
      script.onload = () => this.gisLoaded();
      document.body.appendChild(script);
    } else {
      this.gisLoaded();
    }
  }

  gapiLoaded() {
    gapi.load('client', () => this.initializeGapiClient());
  }

  async initializeGapiClient() {
    await gapi.client.init({
      apiKey: this.API_KEY,
      discoveryDocs: [this.DISCOVERY_DOC],
    });
    this.gapiInited = true;
    this.maybeEnableButtons();

    const token = localStorage.getItem('googleCalendarToken');
    if (token) {
      gapi.client.setToken({ access_token: token });
      await this.getCalendarId();
      await this.listUpcomingEvents();
      this.updateButtons(true);
    }
  }

  gisLoaded() {
    this.tokenClient = google.accounts.oauth2.initTokenClient({
      client_id: this.CLIENT_ID,
      scope: this.SCOPES,
      callback: (resp: any) => this.handleAuthCallback(resp),
    });
    this.gisInited = true;
    this.maybeEnableButtons();


  }

  maybeEnableButtons() {
    const authorizeButton = document.getElementById('authorize_button');
    const signoutButton = document.getElementById('signout_button');

    if (this.gapiInited && this.gisInited && authorizeButton && signoutButton) {
      authorizeButton.style.visibility = 'visible';
      signoutButton.style.visibility = 'hidden';
    }
  }

  updateButtons(isSignedIn: boolean) {
    const authorizeButton = document.getElementById('authorize_button');
    const signoutButton = document.getElementById('signout_button');

    if (authorizeButton && signoutButton) {
      if (isSignedIn) {
        let act = this.actualitza;
        authorizeButton.innerHTML = this.actualitzaText;
        signoutButton.style.visibility = 'visible';
      } else {
        authorizeButton.style.visibility = 'visible';
        signoutButton.style.visibility = 'hidden';
      }
    }
  }

  handleAuthCallback(resp: any) {
    if (resp.error !== undefined) {
      throw resp;
    }
    const signoutButton = document.getElementById('signout_button');
    const authorizeButton = document.getElementById('authorize_button');
    const content = document.getElementById('content');
    if (signoutButton && authorizeButton && content) {
      signoutButton.style.visibility = 'visible';
      authorizeButton.innerHTML = this.actualitzaText;
      this.getCalendarId().then(() => this.listUpcomingEvents());

      localStorage.setItem('googleCalendarToken', gapi.client.getToken().access_token);
      this.isUserLoggedIn = true;
    }
  }

  handleAuthClick() {
    if (gapi.client.getToken() === null) {
      this.tokenClient.requestAccessToken({ prompt: 'consent' });
    } else {
      this.tokenClient.requestAccessToken({ prompt: '' });
    }
  }

  handleSignoutClick() {
    const token = gapi.client.getToken();
    if (token !== null) {
      google.accounts.oauth2.revoke(token.access_token, () => {
        gapi.client.setToken('');
        localStorage.removeItem('googleCalendarToken');
        const content = document.getElementById('content');
        const authorizeButton = document.getElementById('authorize_button');
        const signoutButton = document.getElementById('signout_button');
        if (content && authorizeButton && signoutButton) {
          content.innerText = '';
          authorizeButton.innerHTML = this.autorizeText;
          signoutButton.style.visibility = 'hidden';
          this.isUserLoggedIn = false;
          this.updateCalendarIframe();
        }
      });
    }
  }

  async getCalendarId() {
    try {
      const response = await gapi.client.calendar.calendarList.list();
      const calendars = response.result.items;
      if (calendars && calendars.length > 0) {
        this.calendarId = calendars[0].id;
        this.updateCalendarIframe();
      }
    } catch (err) {
      console.error('Error fetching calendar list: ' + err);
    }
  }

  updateCalendarIframe() {
    const iframe = document.getElementById('google-calendar-iframe') as HTMLIFrameElement;
    if (iframe) {
      iframe.src = `https://calendar.google.com/calendar/embed?src=${this.calendarId}&ctz=Europe%2FMadrid&hl=${this.lang}`;
    }
  }


  async listUpcomingEvents() {
    try {
      const request = {
        calendarId: this.calendarId,
        timeMin: new Date().toISOString(),
        showDeleted: false,
        singleEvents: true,
        maxResults: 10,
        orderBy: 'startTime',
      };
      const response = await gapi.client.calendar.events.list(request);
      const events = response.result.items;
      const content = document.getElementById('content');
      if (!events || events.length === 0) {
        if (content) {
          content.innerHTML = '<ion-item>No events found.</ion-item>';
        }
        return;
      }
      let output = '';
      events.forEach((event: { summary: any; start: { dateTime: any; date: any; }; }) => {
        const startDateTime = new Date(event.start.dateTime || event.start.date);
        const month = startDateTime.toLocaleString('default', { month: 'short' });
        const day = startDateTime.getDate();
        const time = startDateTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        output += `<ion-item style="border-radius: 15px; box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2); margin-bottom: 10px">
              <div style="flex-grow: 1; font-weight: bold">${event.summary}</div>
              <div style="text-align: right;">(${month} ${day}, ${time} h)</div>
            </ion-item>`;
      });
      if (content) {
        content.innerHTML = output;
      }
    } catch ({message}) {
      const content = document.getElementById('content');
      if (content) {
        content.innerHTML = `<ion-item>${message}</ion-item>`;
      }
    }
  }

  async openAddEventModal() {
    const modal = await this.modalController.create({
      component: AddEventModalComponent,
      componentProps: {
        eventForm: this.eventForm
      }
    });
    await modal.present();
    const { data } = await modal.onWillDismiss();
    if (data) {
      this.createEvent(data);
    }
  }

  async createEvent(data: any) {
    const event = {
      summary: data.summary,
      location: data.location,
      description: data.description,
      start: {
        dateTime: `${data.startDate}T${data.startTime}:00`,
        timeZone: 'Europe/Madrid',
      },
      end: {
        dateTime: `${data.endDate}T${data.endTime}:00`,
        timeZone: 'Europe/Madrid',
      },
      reminders: {
        useDefault: false,
        overrides: [
          { method: 'email', minutes: 24 * 60 },
          { method: 'popup', minutes: 10 },
        ],
      },
    };
    try {
      const response = await gapi.client.calendar.events.insert({
        calendarId: this.calendarId,
        resource: event,
      });
    } catch (err) {
      console.error('There was an error contacting the Calendar service: ' + err);
    }
  }
}


@Component({
  selector: 'app-add-event-modal',
  template: `
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ 'ALERTES.AFEGIR' | translate }}</ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="dismiss()">{{ 'MAPS.CANCEL' | translate }}</ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content>
      <form [formGroup]="eventForm">
        <ion-item>
          <ion-label position="floating">{{ 'ALERTES.NOM' | translate }}</ion-label>
          <ion-input formControlName="summary"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label position="floating">{{ 'ALERTES.UBICACIO' | translate }}</ion-label>
          <ion-input formControlName="location"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label position="floating">{{ 'ALERTES.DESCRIPCIO' | translate }}</ion-label>
          <ion-input formControlName="description"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label position="floating">{{ 'ALERTES.DATA_INI' | translate }}</ion-label>
          <ion-input type="date" formControlName="startDate"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label position="floating">{{ 'ALERTES.HORA_INI' | translate }}</ion-label>
          <ion-input type="time" formControlName="startTime"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label position="floating">{{ 'ALERTES.DATA_FI' | translate }}</ion-label>
          <ion-input type="date" formControlName="endDate"></ion-input>
        </ion-item>
        <ion-item>
          <ion-label position="floating">{{ 'ALERTES.HORA_FI' | translate }}</ion-label>
          <ion-input type="time" formControlName="endTime"></ion-input>
        </ion-item>
        <ion-button [disabled]="!eventForm.valid" expand="full" (click)="saveEvent()" color="red" style="text-transform: capitalize; --ion-color-base: #c00000; --ion-border-radius: 20px; font-size: 18px; margin-top: 10%">{{ 'ALERTES.CREAR' | translate }}</ion-button>
      </form>
    </ion-content>
  `,
  standalone: true,
  imports: [
    IonicModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    TranslateModule
  ]
})
export class AddEventModalComponent {
  @Input() eventForm!: FormGroup;
  isUserLoggedIn = localStorage.getItem('googleCalendarToken') !== null;


  constructor(private modalController: ModalController) {}



  dismiss() {
    this.modalController.dismiss();
  }

  saveEvent() {
    if (this.eventForm.valid) {
      this.modalController.dismiss(this.eventForm.value);
    }
  }
}
