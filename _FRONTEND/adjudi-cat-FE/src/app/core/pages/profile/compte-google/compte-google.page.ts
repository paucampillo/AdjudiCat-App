import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {AlertController, IonicModule, NavController} from '@ionic/angular';
import {HeaderGenericoComponent} from "../../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../../services/usuari.service";
import {Router} from "@angular/router";
import {AuthService} from "../../../services/auth.service";
import {addIcons} from "ionicons";
import {calendarOutline, logoGoogle} from "ionicons/icons";

@Component({
  selector: 'app-compte-google',
  templateUrl: './compte-google.page.html',
  styleUrls: ['./compte-google.page.scss'],
  standalone: true,
    imports: [IonicModule, CommonModule, FormsModule, HeaderGenericoComponent, TranslateModule]
})
export class CompteGooglePage implements OnInit {

  titulo: string;
  idUsuari: number;
  private usuari: any;
  calendarToken: boolean = false;
  googleToken: boolean = false;
  private confirm: string;
  private message: string;
  private text: string;
  private logout: string;
  private messageC: string;

  constructor(private navCtrl: NavController, private router: Router, private authService: AuthService, private alertController: AlertController, private usuariServei: UsuariService, private translate: TranslateService) {
    addIcons({logoGoogle});
    addIcons({calendarOutline});

  }

  ngOnInit() {
    this.loadUsuari();
    this.populateGoogle();
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
        this.translate.get('PROFILE-GOOGLE.TITOL').subscribe((res: string) => {
          this.titulo = res;
        });
        this.translate.get('PROFILE.CONFIRMAR').subscribe((res: string) => {
          this.confirm = res;
        });
        this.translate.get('PROFILE.MISSATGE').subscribe((res: string) => {
          this.message = res;
        });
        this.translate.get('MAPS.CANCEL').subscribe((res: string) => {
          this.text = res;
        });
        this.translate.get('PROFILE.TANCAR').subscribe((res: string) => {
          this.logout = res;
        });
        this.translate.get('PROFILE-GOOGLE.MISSATGE').subscribe((res: string) => {
          this.messageC = res;
        });
      });
    }
  }

  populateGoogle() {
    const googleCalendarToken = localStorage.getItem('googleCalendarToken');
    const googleEmailToken = localStorage.getItem('googleEmail');
    const googleNameToken = localStorage.getItem('googleName');
    if (googleCalendarToken !== null) {
      this.calendarToken = true;
    }
    if ((googleEmailToken !== null) && (googleNameToken !== null)) {
      this.googleToken = true;
    }

  }

  desvincularCalendar() {
    this.mostrarConfirmacioCalendar();
  }
  desvincularCompte() {
    this.mostrarConfirmacioLogOut();
  }

  async mostrarConfirmacioCalendar() {
    const alert = await this.alertController.create({
      header: this.confirm,
      message: this.messageC,
      buttons: [
        {
          text: this.text,
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
          }
        }, {
          text: this.logout,
          handler: () => {
            localStorage.removeItem('googleCalendarToken');
            this.calendarToken = false;

          }
        }
      ]
    });
    await alert.present();
  }

  async mostrarConfirmacioLogOut() {
    const alert = await this.alertController.create({
      header: this.confirm,
      message: this.message,
      buttons: [
        {
          text: this.text,
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
          }
        }, {
          text: this.logout,
          handler: () => {
            this.navCtrl.navigateRoot('/login');

          }
        }
      ]
    });
    await alert.present();
  }


}
