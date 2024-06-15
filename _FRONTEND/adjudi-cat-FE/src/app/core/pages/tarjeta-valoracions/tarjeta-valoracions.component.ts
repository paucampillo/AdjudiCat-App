import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AlertController, IonicModule} from "@ionic/angular";
import {addIcons} from "ionicons";
import {trashOutline, star, starHalf} from "ionicons/icons";
import {ValoracioDTO} from "../../model/ValoracioDTO";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {ValoracionsService} from "../../services/valoracions.service";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";
import {Usuari} from "../../model/usuari";

@Component({
  selector: 'app-tarjeta-valoracions',
  templateUrl: './tarjeta-valoracions.component.html',
  styleUrls: ['./tarjeta-valoracions.component.scss'],
  imports: [IonicModule, NgIf, NgForOf, DatePipe, TranslateModule],
  standalone: true,
})
export class TarjetaValoracionsComponent  implements OnInit {

  @Input() valoracio: ValoracioDTO;
  @Input() tipus: string;
  idUsuari: number;
  usuari: Usuari;
  esborrar: boolean;

  @Output() esborrarValoracioEvent = new EventEmitter<any>();

  cancelar: string;
  confirmacio: string;
  confirmar: string;
  esborrarMissatge: string;
  missatgeConfirmacioEsborrada: string;
  missatgePreguntarEsborrar: string;
  titolRebudes: string;
  titolRealitzades: string;

  constructor(private valoracionsServei: ValoracionsService,
              private alertController: AlertController,
              private translate: TranslateService,
              private usuariServei: UsuariService) {
    addIcons({trashOutline});
    addIcons({star});
    addIcons({starHalf});
  }

  ngOnInit() {
    this.loadUsuari();
    if (this.idUsuari === this.valoracio.autor.idUsuari) {
      this.esborrar = true;
    }
    else this.esborrar = false;
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
        this.translate.get('COMUNS.CONFIRMAR').subscribe((res: string) => {
          this.confirmar = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.PREGUNTA_CONFIRMAR_ESBORRAR_VALORACIO').subscribe((res: string) => {
          this.missatgePreguntarEsborrar = res;
        });
        this.translate.get('COMUNS.CANCELAR').subscribe((res: string) => {
          this.cancelar = res;
        });
        this.translate.get('COMUNS.ESBORRA').subscribe((res: string) => {
          this.esborrarMissatge = res;
        });
        this.translate.get('COMUNS.CONFIRMACIÓ').subscribe((res: string) => {
          this.confirmacio = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.CONFIRMACIO_VALORACIO_ESBORRADA').subscribe((res: string) => {
          this.missatgeConfirmacioEsborrada = res;
        });
        this.translate.get('VALORACIONS.TITOL_REBUDES').subscribe((res: string) => {
          this.titolRebudes = res;
        });
        this.translate.get('VALORACIONS.TITOL_REALITZADES').subscribe((res: string) => {
          this.titolRealitzades = res;
        });
      });
    }
  }

  get valoracioMitjanaEntera() {
    return new Array(Math.floor(this.valoracio.puntuacio));
  }

  async esborrarValoracio() {
    const confirm = await this.mostrarConfirmacioEsborrar();
    if (confirm) {
      this.valoracionsServei.deleteValoracio(this.valoracio.idValoracio).subscribe(() => {
        this.mostrarMissatgeValoracioEsborrada();
        this.esborrarValoracioEvent.emit();
      });
    }
  }

  async mostrarConfirmacioEsborrar() {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header: this.confirmar,
        message: this.missatgePreguntarEsborrar,
        buttons: [
          {
            text: this.cancelar,
            role: 'cancel',
            cssClass: 'secondary',
            handler: () => {
              resolve(false);
            }
          }, {
            text: this.esborrarMissatge,
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      await alert.present();
    });
  }

  async mostrarMissatgeValoracioEsborrada() {
    const alert = await this.alertController.create({
      header: this.confirmacio,
      message: this.missatgeConfirmacioEsborrada,
      buttons: ['OK']
    });
    await alert.present();
  }
}
