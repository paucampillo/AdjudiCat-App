import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AlertController, IonicModule} from '@ionic/angular';
import {CanviContrasenya} from "../../../model/canvi-contrasenya";
import {UsuariService} from "../../../services/usuari.service";
import {calcularHashSHA256} from "../../../utils/hash-password";
import {Router} from "@angular/router";
import {HeaderGenericoComponent} from "../../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-canvi-contrasenya',
  templateUrl: './canvi-contrasenya.page.html',
  styleUrls: ['./canvi-contrasenya.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, ReactiveFormsModule, HeaderGenericoComponent, TranslateModule]
})
export class CanviContrasenyaPage implements OnInit {

  formGroup: FormGroup;
  idUsuari: number;
  titulo: string;
  private usuari: any;
  private message2: string;
  private message: string;
  private confirmar: string;
  private error1: string;
  private error: string;
  private canviar: string;
  private cancel: string;

  constructor(private alertController: AlertController,
              private usuariServei: UsuariService,
              private router: Router,
              private translate: TranslateService) { }

  ngOnInit() {
    this.loadUsuari()
    this.loadFormGroup();
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
        this.translate.get('PROFILE-PWD.TITOL').subscribe((res: string) => {
          this.titulo = res;
        });
        this.translate.get('PROFILE.CONFIRMAR').subscribe((res: string) => {
          this.confirmar = res;
        });
        this.translate.get('PROFILE-PWD.ERROR').subscribe((res: string) => {
          this.error = res;
        });
        this.translate.get('PROFILE-PWD.ERROR1').subscribe((res: string) => {
          this.error1 = res;
        });
        this.translate.get('PROFILE-PWD.CANVIAR').subscribe((res: string) => {
          this.canviar = res;
        });
        this.translate.get('MAPS.CANCEL').subscribe((res: string) => {
          this.cancel = res;
        });
        this.translate.get('PROFILE-PWD.MESSAGE2').subscribe((res: string) => {
          this.message2 = res;
        });
        this.translate.get('PROFILE-PWD.MESSAGE').subscribe((res: string) => {
          this.message = res;
        });
      });
    }
  }



  loadFormGroup() {
    this.formGroup = new FormGroup({
      lastPass: new FormControl(null, [Validators.required]),
      newPass: new FormControl(null, [Validators.required]),
      repPass: new FormControl(null, [Validators.required])
    })
  }

  async onSubmit() {
    const confirm = await this.mostrarConfirmacioEditarContrasenya();

    if (confirm) {
      const canvi = new CanviContrasenya();
      if (this.formGroup.get("newPass")?.value != this.formGroup.get("repPass")?.value) {
        const alert = await this.alertController.create({
          header: this.error,
          message: this.error1,
          buttons: ['OK']
        });
        await alert.present();
        return;
      }
      canvi.idUsuari = this.idUsuari;
      canvi.lastPassword = calcularHashSHA256(this.formGroup.get("lastPass")?.value);
      canvi.newPassword = calcularHashSHA256(this.formGroup.get("newPass")?.value);
      this.usuariServei.canviContrasenya(canvi).subscribe(() => {
        this.router.navigate(['/profile']);
        this.mostrarMissatgeContrasenyaModificada();
      });
    }
  }

  async mostrarConfirmacioEditarContrasenya() {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header: this.confirmar,
        message: this.message,
        buttons: [
          {
            text: this.cancel,
            role: 'cancel',
            cssClass: 'secondary',
            handler: () => {
              resolve(false);
            }
          }, {
            text: this.canviar,
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      await alert.present();
    });
  }

  async mostrarMissatgeContrasenyaModificada() {
    const alert = await this.alertController.create({
      header: this.confirmar,
      message: this.message2,
      buttons: ['OK']
    });
    await alert.present();
  }
}
