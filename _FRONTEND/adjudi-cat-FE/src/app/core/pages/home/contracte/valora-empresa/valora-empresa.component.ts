import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {IonicModule, ModalController} from "@ionic/angular";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ValoracioCustomDTO} from "../../../../model/ValoracioCustomDTO";
import {CommonModule} from "@angular/common";
import {ValoracionsService} from "../../../../services/valoracions.service";
import {ValoracioDTO} from "../../../../model/ValoracioDTO";
import {AlertController} from "@ionic/angular";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../../../services/usuari.service";
import {Usuari} from "../../../../model/usuari";

@Component({
  selector: 'app-valora-empresa',
  templateUrl: './valora-empresa.component.html',
  styleUrls: ['./valora-empresa.component.scss'],
  imports: [
    CommonModule,
    IonicModule,
    ReactiveFormsModule,
    FormsModule,
    TranslateModule
  ],
  standalone: true
})
export class ValoraEmpresaComponent  implements OnInit {
  @Input() usuariCreacio: any;
  idUsuari: number;

  usuari: Usuari;

  valoracio: ValoracioCustomDTO = new ValoracioCustomDTO()
  idValoracioJaFeta: number;
  modificada: boolean = false;

  acceptar: string;
  cancelar: string;
  confirmacio: string;
  confirmar: string;
  introdueixValoracio: string;
  missatgeCreadaExit: string;
  missatgeFaltaPuntuacio: string;
  missatgeModificadaExit: string;
  missatgePreguntaConfirmarValoracio: string;
  valora: string;

  constructor(private modalController: ModalController,
              private valoracionsServei: ValoracionsService,
              private alertController: AlertController,
              private translate: TranslateService,
              private usuariServei: UsuariService) {}

  ngOnInit() {
    this.loadUsuari();
    this.valoracio.idAutor = this.idUsuari;
    this.valoracio.idReceptor = this.usuariCreacio.idUsuari;
    this.valoracioExisteix();
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
        this.translate.get('COMUNS.CANCELAR').subscribe((res: string) => {
          this.cancelar = res;
        });
        this.translate.get('HOME.CONTRACTE.VALORA_EMPRESA.VALORA').subscribe((res: string) => {
          this.valora = res;
        });
        this.translate.get('HOME.CONTRACTE.VALORA_EMPRESA.INTRODUEIX_VALORACIO').subscribe((res: string) => {
          this.introdueixValoracio = res;
        });
        this.translate.get('HOME.CONTRACTE.MISSATGE_FALTA_PUNTUACIO').subscribe((res: string) => {
          this.missatgeFaltaPuntuacio = res;
        });
        this.translate.get('COMUNS.ACCEPTAR').subscribe((res: string) => {
          this.acceptar = res;
        });
        this.translate.get('COMUNS.CONFIRMAR').subscribe((res: string) => {
          this.confirmar = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.PREGUNTA_CONFIRMAR_VALORACIO').subscribe((res: string) => {
          this.missatgePreguntaConfirmarValoracio = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.CONFIRMACIO_VALORACIO_CREADA').subscribe((res: string) => {
          this.missatgeCreadaExit = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.CONFIRMACIO_VALORACIO_MODIFICADA').subscribe((res: string) => {
          this.missatgeModificadaExit = res;
        });
        this.translate.get('COMUNS.CONFIRMACIÓ').subscribe((res: string) => {
          this.confirmacio = res;
        });
      });
    }
  }

  valoracioExisteix() {
    const params = { idUsuari: this.idUsuari };
    this.valoracionsServei.listValoracionsEmisor(params).subscribe((response: any) => {
      const valoracions: ValoracioDTO[] = response.content;
      const valoracioExistent = valoracions.find((valoracio: ValoracioDTO) => valoracio.receptor.idUsuari === this.usuariCreacio.idUsuari);
      if (valoracioExistent) {
        this.valoracio.descripcio = valoracioExistent.descripcio;
        this.valoracio.puntuacio = valoracioExistent.puntuacio;
        this.idValoracioJaFeta = valoracioExistent.idValoracio;
      }
    });
  }


  cancel() {
    this.modalController.dismiss({
      dismissed: true
    });
  }


  async confirm() {
    if (this.valoracio.puntuacio === null || this.valoracio.puntuacio === undefined) {
      this.mostrarMissatgeFaltaPuntuacio();
      return;
    }

    const confirm = await this.mostrarConfirmacioValorar();
    if (confirm) {
      this.modalController.dismiss()
      if (this.idValoracioJaFeta !== null && this.idValoracioJaFeta !== undefined) {
        this.valoracionsServei.deleteValoracio(this.idValoracioJaFeta).subscribe();
        this.modificada = true;
      }
      this.valoracionsServei.saveValoracio(this.valoracio).subscribe(() => {
        this.mostrarMissatgeValoracioCreada(this.modificada);
      });
    }
  }

  async mostrarMissatgeFaltaPuntuacio() {
    const alert = await this.alertController.create({
      header: 'Error',
      message: this.missatgeFaltaPuntuacio,
      buttons: [
        {
          text: this.acceptar,
          role: this.cancelar
        }]
      });
      await alert.present();
  }


  async mostrarConfirmacioValorar() {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header: this.confirmar,
        message: this.missatgePreguntaConfirmarValoracio,
        buttons: [
          {
            text: this.cancelar,
            role: 'cancel',
            cssClass: 'secondary',
            handler: () => {
              resolve(false);
            }
          }, {
            text: this.valora,
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      await alert.present();
    });
  }

  async mostrarMissatgeValoracioCreada(modificada: boolean) {
    let missatge = this.missatgeCreadaExit;
    if (modificada) {
      missatge = this.missatgeModificadaExit;
    }

    const alert = await this.alertController.create({
      header: this.confirmacio,
      message: missatge,
      buttons: ['OK']
    });
    await alert.present();
  }
}
