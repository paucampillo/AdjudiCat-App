import {Component, Input, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {AlertController, IonicModule} from '@ionic/angular';
import { OfertaService } from "../../../../services/oferta.service";
import {Contracte} from "../../../../model/contracte";
import {Licitar} from "../../../../model/licitar";
import { Oferta } from '../../../../model/oferta';
import {ContracteService} from "../../../../services/contracte.service";
import {ActivatedRoute} from "@angular/router";
import {HeaderGenericoComponent} from "../../../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../../../services/usuari.service";
import {Usuari} from "../../../../model/usuari";

@Component({
  selector: 'app-licitar',
  templateUrl: './licitar.page.html',
  styleUrls: ['./licitar.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, HeaderGenericoComponent, TranslateModule]
})
export class LicitarPage implements OnInit {

  idUsuari: number;
  usuari: Usuari;

  contracte: Contracte;
  preuMesBaix: number = 0; //Preu oferit més baix
  pressupostC: number; //Pressupost contracte
  nLicitacions: number; //Numero de licitacions realitzades al contracte
  licitacio: number; //variable
  ultimesLicitacions: Oferta[] = []; //array ofertes realitzades al contracte
  increment: number = 100; //increment de la variable
  fontSizeClass: string; //mida del preuMesBaix

  cancelar: string;
  confirmacio: string;
  confirmar: string;
  import: string;
  licita: string;
  missatgeConfirmarLicitar: string;
  missatgePreguntaConfirmarLicitar: string;

  constructor(private route: ActivatedRoute,
              private licitarService: OfertaService,
              private contracteService: ContracteService,
              private alertController: AlertController,
              private translate: TranslateService,
              private usuariServei: UsuariService) {
  }

  ngOnInit() {
    this.loadUsuari();
    this.loadContracte();
    this.calculateFontSize();
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
        this.translate.get('HOME.LICITAR.IMPORT').subscribe((res: string) => {
          this.import = res;
        });
        this.translate.get('HOME.LICITAR.LICITA').subscribe((res: string) => {
          this.licita = res;
        });
        this.translate.get('COMUNS.CONFIRMAR').subscribe((res: string) => {
          this.confirmar = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.PREGUNTA_CONFIRMAR_LICITAR').subscribe((res: string) => {
          this.missatgePreguntaConfirmarLicitar = res;
        });
        this.translate.get('COMUNS.CANCELAR').subscribe((res: string) => {
          this.cancelar = res;
        });
        this.translate.get('COMUNS.CONFIRMACIÓ').subscribe((res: string) => {
          this.confirmacio = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.CONFIRMACIO_LICITACIO_REALITZADA').subscribe((res: string) => {
          this.missatgeConfirmarLicitar = res;
        });
      });
    }
  }


  loadContracte() {
    this.route.params.subscribe(params => {
      const id = params['idContracte'];
      this.contracteService.findContracteId(id, this.idUsuari).subscribe(data => {
        this.contracte = data;
        this.pressupostC = this.licitacio = this.contracte.pressupostLicitacio; // Obtener el presupuesto máximo del contrato
        this.nLicitacions = this.contracte.ofertesRebudes; // Obtener el número de ofertas recibidas
        this.loadOferta();
      });
    });
  }

  loadOferta() {
    this.licitarService.ultimesLicitacions(this.contracte.idContracte).subscribe(ultimesLicitacions => {
      this.ultimesLicitacions = ultimesLicitacions;

      if (this.ultimesLicitacions.length > 0) {
        this.preuMesBaix = this.ultimesLicitacions[0].importAdjudicacioAmbIva;
        this.calculateFontSize();
      }
    });
  }

  calculateFontSize() {
    if (this.preuMesBaix >= 100000000) {
      this.fontSizeClass = 'smaller-font';
    }
    else if (this.preuMesBaix >= 100000) {
      this.fontSizeClass = 'small-font';
    }
    else {
      this.fontSizeClass = 'large-font';
    }
  }

  disminuirPreu() {
    this.increment = parseFloat(String(this.increment));
    // Lògica per disminuir el preu
    if (this.licitacio - this.increment >= 0) {
      this.licitacio -= this.increment;
    }
  }

  augmentarPreu() {
    this.increment = parseFloat(String(this.increment));
    // Lògica per augmentar el preu
    //if (this.licitacio < LICITACIO_MAXIMA) {
    if (this.licitacio + this.increment <= this.pressupostC) {
      this.licitacio += this.increment;
    }
  }

  async mostrarConfirmacioLicitar() {
    const alert = await this.alertController.create({
      header: this.confirmar,
      message: this.missatgePreguntaConfirmarLicitar + this.licitacio + ' € ?',
      buttons: [
        {
          text: this.cancelar,
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {
          }
        }, {
          text: this.licita,
          handler: () => {
            this.licitar();
          }
        }
      ]
    });

    await alert.present();
  }

  licitar() {
    if (this.idUsuari !== null) {
      const licitarData: Licitar = {
        idUsuari: this.idUsuari,
        idContracte: this.contracte.idContracte,
        quantitat: this.licitacio
      };
      // Llamar a la función licitar() del servicio
      this.licitarService.licitar(licitarData).subscribe(
        (res) => {
        this.loadOferta();
        this.mostrarMissatgeHaLictat()
        }
      );
    }
  }

  async mostrarMissatgeHaLictat() {
    const alert = await this.alertController.create({
      header: this.confirmacio,
      message: this.missatgeConfirmarLicitar,
      buttons: ['OK']
    });
    await alert.present();
  }

}
