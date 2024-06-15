import {Component, ElementRef, Inject, Input, OnInit, Renderer2, ViewChild} from '@angular/core';
import {DOCUMENT, NgStyle} from "@angular/common";
import {GooglemapsService} from "./googlemaps.service";
import {ModalController} from "@ionic/angular";
import { Geolocation } from '@capacitor/geolocation';
import { IonicModule } from '@ionic/angular';
import {checkmarkOutline, locateOutline} from "ionicons/icons";
import {addIcons} from "ionicons";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../services/usuari.service";

declare var google: any;

@Component({
  selector: 'app-googlemaps',
  templateUrl: './googlemaps.component.html',
  styleUrls: ['./googlemaps.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    NgStyle,
    TranslateModule
  ],
})
export class GooglemapsComponent  implements OnInit {

  @Input() position = {
    //Ubicació Barcelona ciutat
    lat: 41.38879,
    lng:  2.15899
  }
  @Input() userLocation: { lat: number, lng: number };

  label = {
    titulo: '',
    subtitulo: ''
  }

  map: any;
  marker: any;
  infoWindow: any;
  positionSet: any;

  @ViewChild('map') divMap: ElementRef;
  private idUsuari: number;
  private usuari: any;

  constructor(private renderer: Renderer2,
              @Inject(DOCUMENT) private document: Document,
              private googlemapsService: GooglemapsService,
              public modalController: ModalController, private translate: TranslateService, private usuariServei: UsuariService) {
              addIcons({locateOutline});
              addIcons({checkmarkOutline});
  }

  ngOnInit() {
    this.loadUsuari();
    this.init();
    if (this.userLocation) {
      this.position = this.userLocation;
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
        } else if (this.usuari.idioma?.codi == 'CAST') {
          this.translate.use('es');
        } else {
          this.translate.use('ca');
        }
        this.translate.get('MAPS.TITOL_UBI').subscribe((res: string) => {
          this.label.titulo = res;
          this.translate.get('MAPS.SUBTITOL_UBI').subscribe((res: string) => {
            this.label.subtitulo = res;
            if (this.label.titulo.length) {
              this.addMarker(this.position);
              this.setInfoWindow(this.marker,this.label.titulo,this.label.subtitulo);
            }
          });
        });
      });
    }
  }

  async init() {
    await this.googlemapsService.init(this.renderer, this.document);
    if (this.googlemapsService.mapsLoaded) {
      this.initMap();
      //this.clickHandleEvent();
    } else {
      // Wait until this.googlemapsService.mapsLoaded becomes true
    }
  }

  initMap() {
    const position = this.position;

    let latLng = new google.maps.LatLng(position.lat, position.lng);
    let mapOptions = {
      center: latLng,
      zoom: 8,
      disableDefaultUI: false,
      clickableIcons: true,
    };

    this.map = new google.maps.Map(this.divMap.nativeElement, mapOptions);
    this.marker = new google.maps.Marker({
      map: this.map,
      draggable: true,
      animation: google.maps.Animation.DROP,
    });
    //this.clickHandleEvent();
    this.infoWindow = new google.maps.InfoWindow();
    if (this.label.titulo.length) {
      this.addMarker(position);
      this.setInfoWindow(this.marker,this.label.titulo,this.label.subtitulo);
    }
  }

  //TODO: necessari?
  clickHandleEvent() {
    google.maps.event.addListener(this.map, 'click', (event: any) => {
      const position = {
        lat: event.latLng.lat(),
        lng: event.latLng.lng()
      }
      this.addMarker(position);
      this.setInfoWindow(this.marker,this.label.titulo,this.label.subtitulo);
    });
  }

  addMarker(position: any) {
    if (typeof google === 'undefined') {
      setTimeout(() => this.addMarker(position), 1000);
      return;
    }
    let latLng = new google.maps.LatLng(position.lat, position.lng);

    this.marker.setPosition(latLng);
    this.map.panTo(position);
    this.positionSet = position;
  }

  setInfoWindow(marker: any, titulo: string, subtitulo: string) {
    if (typeof google === 'undefined') {
      setTimeout(() => this.setInfoWindow(marker, titulo, subtitulo), 1000);
      return;
    }

    if (!this.infoWindow) {
      this.infoWindow = new google.maps.InfoWindow();
    }
    let contentString = '<div id="contentInsideMap">' +
                               '<div>' +
                               '</div>' +
                               '<p style="font-weight: bold; margin-bottom: 5px; color: black;">' +
                               titulo + '</p>' +
                               '<p style="color:black;" class="normal m-0">' +
                               subtitulo + '</p>' +
                               '</div>' +
                               '</div>';
    this.infoWindow.setContent(contentString);
    this.infoWindow.open(this.map, marker);
  }

  async mylocation() {
    await Geolocation.getCurrentPosition().then((res: any) => {
      const position = {
        lat: res.coords.latitude,
        lng: res.coords.longitude
      }
      this.addMarker(position);
    });
  }

  //TODO: fix modal
  aceptar() {
    this.modalController.dismiss(this.positionSet);
  }

  cerrarModal() {
    this.modalController.dismiss();
  }



}
