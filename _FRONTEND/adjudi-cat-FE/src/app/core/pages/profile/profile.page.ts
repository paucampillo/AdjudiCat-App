import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {AlertController, IonicModule, NavController} from '@ionic/angular';
import {MenuComponent} from "../menu/menu.component";
import {addIcons} from "ionicons";
import {chevronForwardOutline} from "ionicons/icons";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, MenuComponent, HeaderGenericoComponent, TranslateModule]
})
export class ProfilePage implements OnInit {

  isGoogleUser = {google: false};
  codiRol: string;
  idUsuari: number;
  titulo: string;
  private usuari: any;
  confirm: string;
  message: string;
  text: string;
  logout: string;

  constructor(private navCtrl: NavController, private router: Router, private authService: AuthService, private alertController: AlertController, private usuariServei: UsuariService, private translate: TranslateService) {
    addIcons({chevronForwardOutline});
  }

  ngOnInit() {
    this.populateGoogleData();
    this.loadUsuari();
  }

  loadUsuari() {
    const idUsuariStr = localStorage.getItem('idUsuari');
    const codiRolStr = localStorage.getItem('codiRol');
    if (codiRolStr !== null) {
      this.codiRol = codiRolStr;
    }
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
        this.translate.get('PROFILE.TITOL').subscribe((res: string) => {
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
      });
    }
  }

  populateGoogleData() {
    const googleEmail = localStorage.getItem('googleEmail');
    const googleName = localStorage.getItem('googleName');

    if ((googleEmail != null) && (googleName != null)) {
      this.isGoogleUser.google = true;
    }
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


  navigateTo(option: number) {
    let tipusVal: 'rebudes' | 'realitzades';

    switch (option) {
      case 1:
        this.navCtrl.navigateRoot('/profile/edit');
        break;
      case 2:
        this.navCtrl.navigateRoot('/profile/password');
        break;
      case 3:
        this.navCtrl.navigateRoot('/profile/gestio-usuaris');
        break;
      case 4:
        tipusVal = 'rebudes';
        this.navCtrl.navigateRoot(['/valoracions', tipusVal]);
        break;
      case 5:
        tipusVal = 'realitzades';
        this.navCtrl.navigateRoot(['/valoracions', tipusVal]);
        break;
      case 6:
        this.navCtrl.navigateRoot('/profile/compte-google');
        break;
      case 8:
        const userID = localStorage.getItem('idUsuari');
        this.navCtrl.navigateRoot('/chat-mensajes/' + userID + '/19');
        break;
      case 9:
        this.mostrarConfirmacioLogOut();
        break;
      default:
        break;
    }
  }
}
