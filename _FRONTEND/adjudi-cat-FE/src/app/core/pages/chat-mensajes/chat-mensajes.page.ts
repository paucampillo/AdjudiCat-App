import {Component, OnInit, ViewChild, ElementRef, OnDestroy, AfterViewInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import {MenuComponent} from "../menu/menu.component";
import { IonTextarea } from '@ionic/angular';
import {addIcons} from "ionicons";
import {send, trash} from "ionicons/icons";
import {ActivatedRoute, Router} from '@angular/router';
import {Missatge} from "../../model/missatge";
import {MissatgeService} from "../../services/missatge.service";
import {UsuariService} from "../../services/usuari.service";
import {Usuari} from "../../model/usuari";
import {TarjetaContacteComponent} from "../tarjeta-contacte/tarjeta-contacte.component";
import {AlertController} from '@ionic/angular';
import { IonContent } from '@ionic/angular';
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-chat-mensajes',
  templateUrl: './chat-mensajes.page.html',
  styleUrls: ['./chat-mensajes.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, MenuComponent, TarjetaContacteComponent, HeaderGenericoComponent, TranslateModule]
})
export class ChatMensajesPage implements OnInit, OnDestroy, AfterViewInit{
  mensaje: string = 'No hay mensajes históricos';
  @ViewChild('myTextarea') myTextarea: IonTextarea;
  @ViewChild('myTextarea', { static: false, read: ElementRef }) myTextareaRef: ElementRef<HTMLIonTextareaElement>;
  @ViewChild(IonContent, { static: true }) content: IonContent;
  @ViewChild('listaMensajes', { read: ElementRef }) listaMensajes: ElementRef;
  userID: number;
  contacteID: number;
  receptor: Usuari;
  contenido: string = '';
  mensajesList: Missatge[] = [];
  private intervalId: any;
  fechaActual: Date = new Date();
  mensajesSize: number;

  usuari: Usuari;

  escribeUnMensaje: string;

  constructor(private route: ActivatedRoute,
              private missatgeServei: MissatgeService,
              private usuariServei: UsuariService,
              private alertController: AlertController,
              private router: Router,
              private translate: TranslateService) {
    addIcons({send});
    addIcons({trash});
    this.intervalId = setInterval(() => {
      this.loadMensajes();
    }, 30000);
  }

  ngOnInit() {
    this.userID = Number(this.route.snapshot.paramMap.get('idUserEmisor'));
    this.contacteID = Number(this.route.snapshot.paramMap.get('idUserReceptor'));
    this.loadReceptor();
    this.loadMensajes();
    this.content.scrollToBottom(0);
    this.loadUsuari();
  }

  ngAfterViewInit(){
    this.scrollToBottom();

    this.content.ionScrollEnd.subscribe(() => {
      this.scrollToBottom();
    })
  }

  loadUsuari() {
    const idUsuariStr = localStorage.getItem('idUsuari');
    if (idUsuariStr !== null) {
      this.userID = parseInt(idUsuariStr, 10);
      this.usuariServei.getUsuari(this.userID).subscribe(data => {
        this.usuari = data;
        if (this.usuari.idioma?.codi === 'ENG') {
          this.translate.use('en');
        } else if (this.usuari.idioma?.codi === 'CAST') {
          this.translate.use('es');
        } else {
          this.translate.use('ca');
        }
        this.translate.get('CHAT_MENSAJES.ESCRIU_MISSATGE').subscribe((res: string) => {
          this.escribeUnMensaje = res;
        });
      });
    }
  }

  ionViewDidEnter() {
    this.content.scrollToBottom(300);
  }

  scrollToBottom(): void {
    if (this.listaMensajes && this.listaMensajes.nativeElement) {
      this.listaMensajes.nativeElement.scrollTop = this.listaMensajes.nativeElement.scrollHeight;
    }
  }

  loadReceptor(){
    this.usuariServei.getUsuari(this.contacteID).subscribe(data => {
      this.receptor = data;
    })
  }

  loadMensajes() {
    this.missatgeServei.findMensajes(this.userID, this.contacteID).subscribe(data => {
      this.mensajesList = data.content;
      this.mensajesSize = this.mensajesList.length;
      const n: number = this.mensajesList.length;
      for(let i = 0; i < n; ++i) {
        if (!(this.mensajesList[i].dataHoraEnvio instanceof Date)) {
          this.mensajesList[i].dataHoraEnvio = new Date(this.mensajesList[i].dataHoraEnvio);
        }
      }
      this.content.scrollToBottom(0);
    });
  }

  enviarMensaje() {
    if (this.contenido != '') {
      if (this.contacteID == 19) {
        this.missatgeServei.enviarChatbot(this.userID, this.contenido).subscribe(data => {
          this.contenido = ''; //una vez enviado, dejar el campo mensaje limpio
          this.loadMensajes();
          this.restablecerAlturaInicial();
        })
      }
      else {
        this.missatgeServei.enviarMissatge(this.userID, this.contacteID, this.contenido).subscribe(
          () => {
            this.contenido = ''; //una vez enviado, dejar el campo mensaje limpio
            this.loadMensajes();
            this.restablecerAlturaInicial();
            //this.loadMensajes();
            //this.router.navigate(['/chat-mensajes'+'/'+this.userID+'/'+this.contacteID]);
          })
      }
    }
  }

  ajustarAlturaTextarea() {
    const textarea = this.myTextareaRef.nativeElement;
    textarea.style.height = 'auto';
    textarea.style.height = `${Math.min(textarea.scrollHeight, 300)}px`;
  }

  restablecerAlturaInicial() {
    const textarea = this.myTextareaRef.nativeElement;
    textarea.style.height = '40px';
  }

  restablecerAlturaSiVacio() {
    const textarea = this.myTextareaRef.nativeElement;
    if (textarea.value?.trim() === '') {
      textarea.style.height = '40px';
    }
  }

  async mostrarConfirmacionEliminarMensaje(idMissatge: number) {
    const alert = await this.alertController.create({
      header: 'Eliminar missatge',
      message: 'Estàs segur/a que desitges eliminar aquest missatge?',
      buttons: [
        {
          text: 'Cancel·lar',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
          }
        },
        {
          text: 'Eliminar',
          handler: () => {
            this.missatgeServei.eliminarMissatge(idMissatge).subscribe(() => {
              this.loadMensajes();
              this.mostrarMissatgeEliminat();
              //this.router.navigate(['/chat-mensajes'+'/'+this.userID+'/'+this.contacteID]);
            })
          }
        }
      ]
    });
    await alert.present();
  }

  async mostrarMissatgeEliminat() {
    const alert = await this.alertController.create({
      header: 'Confirmació',
      message: 'El missatge ha estat eliminat amb èxit.',
      buttons: ['OK']
    });
    await alert.present();
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
}
