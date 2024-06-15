import {Component, Input, OnInit} from '@angular/core';
import {
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonTabs,
  IonTabBar,
  IonTabButton,
  IonIcon, IonLabel
} from '@ionic/angular/standalone';
import { IonApp, IonRouterOutlet } from '@ionic/angular/standalone';
import {addIcons} from "ionicons";
import {
  receiptOutline,
  searchOutline,
  chatbubbles,
  chatbubblesOutline,
  notificationsOutline,
  personOutline,
  search, receipt, notifications, person
} from "ionicons/icons";
import {NgIf} from "@angular/common";
import {Usuari} from "../../model/usuari";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";


@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  standalone: true,
  imports: [IonApp, IonRouterOutlet, IonHeader, IonToolbar, IonTitle, IonContent, IonTabs, IonTabBar, IonTabButton, IonIcon, IonLabel, NgIf, TranslateModule],
})
export class MenuComponent  {

  @Input() filled: number = 1;
  constructor(private translate: TranslateService,
              private usuariServei: UsuariService) {
    addIcons({search, searchOutline, receipt, receiptOutline, chatbubbles, chatbubblesOutline,
    notifications, notificationsOutline, person, personOutline});
  }

  idUsuari: number;
  usuari: Usuari;

  ngOnInit() {
    this.loadUsuari();
  }

  loadUsuari() {
    const idUsuariStr = localStorage.getItem('idUsuari');
    if (idUsuariStr !== null) {
      this.idUsuari = parseInt(idUsuariStr, 10);
      this.usuariServei.getUsuari(this.idUsuari).subscribe(data => {
        this.usuari = data;
        if (this.usuari.idioma?.codi === 'ENG') {
          this.translate.use('en');
        } else if (this.usuari.idioma?.codi === 'CAST') {
          this.translate.use('es');
        } else {
          this.translate.use('ca');
        }
      });
    }
  }


}
