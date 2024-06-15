import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {AlertController, IonicModule} from '@ionic/angular';
import {Usuari} from "../../../model/usuari";
import {Idioma} from "../../../model/idioma";
import {IdiomesService} from "../../../services/idiomes.service";
import {UsuariService} from "../../../services/usuari.service";
import {Organ} from "../../../model/organ";
import {OrganService} from "../../../services/organ.service";
import {addIcons} from "ionicons";
import {searchOutline} from "ionicons/icons";
import {Router} from "@angular/router";
import {HeaderGenericoComponent} from "../../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-editar-perfil-extern',
  templateUrl: './editar-perfil.page.html',
  styleUrls: ['./editar-perfil.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, ReactiveFormsModule, HeaderGenericoComponent, TranslateModule]
})
export class EditarPerfilPage implements OnInit {

  formGroup: FormGroup;
  idUsuari: number;
  codiRol: string;
  usuari: Usuari;
  idiomasList: Idioma[] = [];
  organsList: Organ[] = [];
  titulo: string;
  selectO: any;
  cancel: any;
  accepta: any;
  selectL: any;
  selectN: any;
  private desar: string;
  private message: string;
  private message2: string;
  private confirmacio: string;

  constructor(private idiomesServce: IdiomesService,
              private usuariService: UsuariService,
              private organService: OrganService,
              private alertController: AlertController,
              private router: Router,
              private translate: TranslateService) {
    addIcons({searchOutline});
  }

  ngOnInit() {
    this.loadUsuari();
    this.loadIdiomas();
    this.loadOrgans();
  }

  loadUsuari() {
    const idUsuariStr = localStorage.getItem('idUsuari');
    const codiRolStr = localStorage.getItem('codiRol');
    if (codiRolStr !== null) {
      this.codiRol = codiRolStr;
    }
    if (idUsuariStr !== null) {
      this.idUsuari = parseInt(idUsuariStr, 10);
      this.usuariService.getUsuari(this.idUsuari).subscribe(data => {
        this.usuari = data;
        if (this.usuari.idioma?.codi === 'ENG') {
          this.translate.use('en');
        } else if (this.usuari.idioma?.codi === 'CAST') {
          this.translate.use('es');
        } else {
          this.translate.use('ca');
        }
        this.translate.get('PROFILE-EDIT.TITOL').subscribe((res: string) => {
          this.titulo = res;
        });
        this.translate.get('PROFILE-EDIT.SELECT_ORGAN').subscribe((res: string) => {
          this.selectO = res;
        });
        this.translate.get('MAPS.CANCEL').subscribe((res: string) => {
          this.cancel = res;
        });
        this.translate.get('PROFILE.CONFIRMAR').subscribe((res: string) => {
          this.accepta = res;
        });
        this.translate.get('PROFILE-EDIT.SELECT_LANG').subscribe((res: string) => {
          this.selectL = res;
        });
        this.translate.get('PROFILE-EDIT.SELECT_NOM').subscribe((res: string) => {
          this.selectN = res;
        });
        this.translate.get('PROFILE.DESAR').subscribe((res: string) => {
          this.desar = res;
        });
        this.translate.get('PROFILE-EDIT.MISSATGE').subscribe((res: string) => {
          this.message = res;
        });
        this.translate.get('PROFILE.MISSATGE2').subscribe((res: string) => {
          this.message2 = res;
        });
        this.translate.get('PROFILE.CONFIRMACIO').subscribe((res: string) => {
          this.confirmacio = res;
        });

        this.loadFormGroup();
      });
    }
  }

  loadFormGroup() {
    this.formGroup = new FormGroup({
      nom: new FormControl(this.usuari?.nom, [Validators.required]),
      email:new FormControl(this.usuari?.email, [Validators.required, Validators.email]),
      pais: new FormControl(this.usuari?.pais),
      cp: new FormControl(this.usuari?.codiPostal),
      direccio: new FormControl(this.usuari?.direccio),
      telefon: new FormControl(this.usuari?.telefon),
      web: new FormControl(this.usuari?.enllacPerfilSocial),
      nif: new FormControl(this.usuari?.identNif),
      idioma: new FormControl(this.usuari?.idioma?.nom, [Validators.required]),
      notificacions: new FormControl(this.usuari?.notificacionsActives),
      descripcio: new FormControl(this.usuari?.descripcio),
      nomOrgan: new FormControl(''),
      organ: new FormControl(this.usuari?.organ?.nom)
    })
  }

  loadIdiomas() {
    this.idiomesServce.listar().subscribe(data => {
      this.idiomasList = data;
    })
  }

  loadOrgans() {
    let nomOrgan = '';
    if (this.formGroup != undefined) {
      nomOrgan = this.formGroup.get('nomOrgan')?.value;
    }
    const nombre = {
      nom : nomOrgan
    };
    this.organService.buscar(nombre).subscribe(data => {
      this.organsList = data;
    })
  }

  async onSubmit() {
    const confirm = await this.mostrarConfirmacioEditarDades();

    if (confirm) {
      this.usuari.nom = this.formGroup.get("nom")?.value;
      this.usuari.email = this.formGroup.get("email")?.value;
      this.usuari.pais = this.formGroup.get("pais")?.value;
      this.usuari.codiPostal = this.formGroup.get("cp")?.value;
      this.usuari.direccio = this.formGroup.get("direccio")?.value;
      this.usuari.telefon = this.formGroup.get("telefon")?.value;
      this.usuari.identNif = this.formGroup.get("nif")?.value;
      this.usuari.enllacPerfilSocial = this.formGroup.get("web")?.value;
      this.usuari.notificacionsActives = this.formGroup.get("notificacions")?.value;
      this.usuari.idioma = this.idiomasList.find(idi => idi.nom == this.formGroup.get("idioma")?.value);
      this.usuari.organ = this.organsList.find(org => org.nom == this.formGroup.get("organ")?.value);
      this.usuari.descripcio = this.formGroup.get("descripcio")?.value;
      this.usuariService.edit(this.usuari).subscribe(() => {
        this.router.navigate(['/profile']);
        this.mostrarMissatgeDadesEditades();
      });
    }
  }

  async mostrarConfirmacioEditarDades() {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header: this.accepta,
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
            text: this.desar,
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      await alert.present();
    });
  }

  async mostrarMissatgeDadesEditades() {
    const alert = await this.alertController.create({
      header: this.confirmacio,
      message: this.message2,
      buttons: ['OK']
    });
    await alert.present();
  }
}
