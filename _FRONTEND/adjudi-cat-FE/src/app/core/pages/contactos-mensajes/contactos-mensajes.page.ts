import {Component, Input, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {AlertController, IonicModule} from '@ionic/angular';
import {MenuComponent} from "../menu/menu.component";
import {TarjetaContacteComponent} from "../tarjeta-contacte/tarjeta-contacte.component";
import {ContacteService} from "../../services/contacte.service";
import {Router, RouterLink} from "@angular/router";
import {Conversacion} from "../../model/conversacion";
import {trash} from "ionicons/icons";
import {addIcons} from "ionicons";
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";
import {Usuari} from "../../model/usuari";

@Component({
  selector: 'app-contactos-mensajes',
  templateUrl: './contactos-mensajes.page.html',
  styleUrls: ['./contactos-mensajes.page.scss'],
  standalone: true,
  imports: [MenuComponent, IonicModule, CommonModule, FormsModule, TarjetaContacteComponent, RouterLink, HeaderGenericoComponent, TranslateModule]
})
export class ContactosMensajesPage implements OnInit {
  contactesList: Conversacion[];
  userID :string = '';
  searchTerm: string = '';
  prevSearchTermLength: number = 0;
  mostrarEliminar: boolean = false;
  idUsuari: number;

  usuari: Usuari;

  titulo: string;
  buscarContacte: string;

  constructor(private contacteServei: ContacteService,
              private router: Router,
              private alertController: AlertController,
              private translate: TranslateService,
              private usuariServei: UsuariService) {
    addIcons({trash});
  }

  ngOnInit() {
    this.loadUsuari();
    this.loadContactos();
  }

  loadContactos() {
    this.contacteServei.findContactes(this.userID).subscribe(data => {
      this.contactesList = data;
      if(!this.contactesList ) {
      }
      else{
      }
    })
  }

  loadUsuari() {
    const usuariIDStr = localStorage.getItem('idUsuari');
    if (usuariIDStr !== null) {
      this.userID = usuariIDStr;
      this.idUsuari = parseInt(usuariIDStr, 10);
      this.usuariServei.getUsuari(this.idUsuari).subscribe(data => {
        this.usuari = data;
        if (this.usuari.idioma?.codi === 'ENG') {
          this.translate.use('en');
        } else if (this.usuari.idioma?.codi === 'CAST') {
          this.translate.use('es');
        } else {
          this.translate.use('ca');
        }
        this.translate.get('MENU.MISSATGES').subscribe((res: string) => {
          this.titulo = res;
        });
        this.translate.get('CONTACTOS_MENSAJES.BUSCAR_CONTACTE').subscribe((res: string) => {
          this.buscarContacte = res;
        });
      });
    }
  }

  anarMissatge(contacte: Conversacion) {
    this.router.navigate(['/chat-mensajes'+'/'+this.userID+'/'+contacte.idUsuari]);
  }

  findContactesBuscador() {
    if (this.searchTerm.trim().length < (this.prevSearchTermLength ?? 0)) {
      this.loadContactos();
    }
    this.prevSearchTermLength = this.searchTerm.trim().length;

    if (this.searchTerm.trim() !== '') {
      this.contactesList = this.contactesList.filter(contacto =>
        contacto.nom.toLowerCase().includes(this.searchTerm.toLowerCase())
      );
    }
    else this.loadContactos();
  }

  mostrarEliminarContactos() {
    this.mostrarEliminar = !this.mostrarEliminar;
  }

  async eliminarContacto(contacte: Conversacion){
    const confirm = await this.mostrarConfirmacioEliminarConversa();
    if (confirm) {
      this.contacteServei.eliminarConversa(this.userID, contacte.idUsuari).subscribe(() => {
        this.loadContactos();
        this.mostrarMissatgeConversaEliminada();
      })
    }
  }


  async mostrarConfirmacioEliminarConversa() {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header: 'Confirmar',
        message: 'Estàs segur/a que desitges eliminar aquesta conversa?',
        buttons: [
          {
            text: 'Cancel·lar',
            role: 'cancel',
            cssClass: 'secondary',
            handler: () => {
              resolve(false);
            }
          }, {
            text: 'Esborrar',
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      await alert.present();
    });
  }

  async mostrarMissatgeConversaEliminada() {
    const alert = await this.alertController.create({
      header: 'Confirmació',
      message: 'La conversa s\'ha eliminat amb èxit.',
      buttons: ['OK']
    });
    await alert.present();
  }
}
