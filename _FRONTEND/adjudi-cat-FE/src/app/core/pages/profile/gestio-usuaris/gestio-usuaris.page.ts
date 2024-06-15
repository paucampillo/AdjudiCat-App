import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {AlertController, IonicModule } from '@ionic/angular';
import {MenuComponent} from "../../menu/menu.component";
import {TarjetaContacteComponent} from "../../tarjeta-contacte/tarjeta-contacte.component";
import {Usuari} from "../../../model/usuari";
import {TarjetaContracteComponent} from "../../tarjeta-contracte/tarjeta-contracte.component";
import {TarjetaValoracionsComponent} from "../../tarjeta-valoracions/tarjeta-valoracions.component";
import {HeaderGenericoComponent} from "../../ComponentesCabecera/header-generico/header-generico.component";
import {addIcons} from "ionicons";
import {lockClosed, lockOpen} from "ionicons/icons";
import {UsuariService} from "../../../services/usuari.service";
import {TranslateModule, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-gestio-usuaris',
  templateUrl: './gestio-usuaris.page.html',
  styleUrls: ['./gestio-usuaris.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, MenuComponent, TarjetaContacteComponent, TarjetaContracteComponent, TarjetaValoracionsComponent, HeaderGenericoComponent, TranslateModule],
})
export class GestioUsuarisPage implements OnInit {
  searchTerm: string = '';
  usuariList: Usuari[] = [];
  missatgeUsuarisBuit: string = "No hi ha cap usuari";
  private prevSearchTermLength: number = 0;
  private idUsuari: number;
  private usuari: any;
  private mensaje: any;
  protected titulo: string;
  protected nomUsuari: string;
  protected estatUsuari: string;
  cercar: any;
  confirmacio: string;

  constructor(private usuariServei: UsuariService, private alertController: AlertController, private translate: TranslateService,) {
    addIcons({lockOpen});
    addIcons({lockClosed});
  }

  ngOnInit() {
    this.loadUsuaris();
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
        this.translate.get('PROFILE-US.TITOL').subscribe((res: string) => {
          this.titulo = res;
        });
        this.translate.get('PROFILE-US.CERCAR').subscribe((res: string) => {
          this.cercar = res;
        });
        this.translate.get('PROFILE-US.MISSATGE').subscribe((res: string) => {
          this.missatgeUsuarisBuit = res;
        });
        this.translate.get('PROFILE-US.NOM').subscribe((res: string) => {
          this.nomUsuari = res;
        });
        this.translate.get('PROFILE-US.ESTAT').subscribe((res: string) => {
          this.estatUsuari = res;
        });
        this.translate.get('COMUNS.CONFIRMACIÓ').subscribe((res: string) => {
          this.confirmacio = res;
        });
      });
    }
  }

  findUsuarisBuscador() {
    if (this.searchTerm.trim().length < (this.prevSearchTermLength ?? 0)) {
      this.loadUsuaris();
    }
    this.prevSearchTermLength = this.searchTerm.trim().length;

    if (this.searchTerm.trim() !== '') {
      this.usuariList = this.usuariList.filter(usuari =>
        usuari.nom.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
    else this.loadUsuaris();
  }

  modificarEstadoBloquedo(usuari: Usuari) {
    if (usuari.bloquejat){
      this.usuariServei.desbloquejarUsuari(usuari.idUsuari).subscribe( () => {
        this.loadUsuaris();
        this.mostrarMissatgeUsuariDesbloquejat(usuari.idUsuari);
      });
    }
    else {
      this.usuariServei.bloquejarUsuari(usuari.idUsuari).subscribe( () => {
        this.loadUsuaris();
        this.mostrarMissatgeUsuariBloquejat(usuari.idUsuari);
      });

    }
  }

  loadUsuaris() {
    this.usuariServei.findAllUsuaris().subscribe(data => {
      this.usuariList = data.content;
    });
  }

  async mostrarMissatgeUsuariBloquejat(idUsuari: any) {
    if (this.usuari.idioma?.codi === 'ENG') {
      this.mensaje = 'The user with id=' + idUsuari + ' has been successfully blocked.';
    } else if (this.usuari.idioma?.codi === 'CAST') {
      this.mensaje = 'El usuario con id='+idUsuari+' se ha bloquejado con éxito.';
    } else {
      this.mensaje = 'L\'usuari amb id='+idUsuari+' s\'ha bloquejat amb èxit.';
    }
    const alert = await this.alertController.create({
      header: this.confirmacio,
      message: this.mensaje,
      buttons: ['OK']
    });
    await alert.present();
  }

  async mostrarMissatgeUsuariDesbloquejat(idUsuari: any) {
    if (this.usuari.idioma?.codi === 'ENG') {
      this.mensaje = 'The user with id=' + idUsuari + ' has been successfully unblocked.';
    } else if (this.usuari.idioma?.codi === 'CAST') {
      this.mensaje = 'El usuario con id='+idUsuari+' se ha desbloquejado con éxito.';
    } else {
      this.mensaje = 'L\'usuari amb id='+idUsuari+' s\'ha desbloquejat amb èxit.';
    }
    const alert = await this.alertController.create({
      header: this.confirmacio,
      message: this.mensaje,
      buttons: ['OK']
    });
    await alert.present();
  }
}
