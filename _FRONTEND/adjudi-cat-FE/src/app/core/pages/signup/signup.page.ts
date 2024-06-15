import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {IonicModule, NavController} from '@ionic/angular';
import {Idioma} from "../../model/idioma";
import {IdiomesService} from "../../services/idiomes.service";
import {RolsService} from "../../services/rols.service";
import {Rols} from "../../model/rols";
import {Usuari} from "../../model/usuari";
import {calcularHashSHA256} from "../../utils/hash-password";
import {UsuariService} from "../../services/usuari.service";
import {ReturnLogin} from "../../model/return-login";
import {addIcons} from "ionicons";
import {searchOutline} from "ionicons/icons";
import {OrganService} from "../../services/organ.service";
import {Organ} from "../../model/organ";
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.page.html',
  styleUrls: ['./signup.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, ReactiveFormsModule, HeaderGenericoComponent]
})
export class SignupPage implements OnInit {

  public formGroup: FormGroup;
  idiomasList: Idioma[] = [];
  rolsList: Rols[] = [];
  organsList: Organ[];
  retLogin: ReturnLogin;
  isGoogleUser = {google: false};

  constructor(private idiomesServce: IdiomesService,
              private rolsService: RolsService,
              private usuariService: UsuariService,
              private navCtrl: NavController,
              private organService: OrganService) {
    addIcons({searchOutline});
  }

  ngOnInit() {
    this.loadFormGroup();
    this.loadIdiomas();
    this.loadRols();
    this.loadOrgans();
    this.populateGoogleData();

  }

  private loadFormGroup() {
    this.formGroup = new FormGroup({
      nom: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required, Validators.email]),
      contrasenya: new FormControl(null, this.isGoogleUser ? null : [Validators.required]),
      pais: new FormControl(null),
      cp: new FormControl(null),
      direccio: new FormControl(null),
      telefon: new FormControl(null),
      nif: new FormControl(null),
      web: new FormControl(null),
      idioma: new FormControl(null, [Validators.required]),
      notificacions: new FormControl(false),
      rol: new FormControl(null, [Validators.required]),
      nomOrgan: new FormControl(''),
      organ: new FormControl(null)
    });
  }

  populateGoogleData() {
    const googleEmail = localStorage.getItem('googleEmail');
    const googleName = localStorage.getItem('googleName');

    if (googleEmail && googleName) {
      this.formGroup.patchValue({
        email: googleEmail,
        nom: googleName,
        contrasenya: 'null'
      });
      this.isGoogleUser.google = true;
    }
  }

  loadIdiomas() {
    this.idiomesServce.listar().subscribe(data => {
      this.idiomasList = data;
    })
  }

  loadRols() {
    this.rolsService.listar().subscribe(data => {
      this.rolsList = data;
    })
  }

  loadOrgans() {
    const nombre = {
      nom : this.formGroup.get('nomOrgan')?.value
    };
    this.organService.buscar(nombre).subscribe(data => {
      this.organsList = data;
    })
  }

  onSubmit() {
    const usuari = new Usuari();
    usuari.nom = this.formGroup.get("nom")?.value;
    usuari.email = this.formGroup.get("email")?.value;
    usuari.contrasenya = calcularHashSHA256(this.formGroup.get("contrasenya")?.value);
    usuari.pais = this.formGroup.get("pais")?.value;
    usuari.codiPostal = this.formGroup.get("cp")?.value;
    usuari.direccio = this.formGroup.get("direccio")?.value;
    usuari.telefon = this.formGroup.get("telefon")?.value;
    usuari.identNif = this.formGroup.get("nif")?.value;
    usuari.enllacPerfilSocial = this.formGroup.get("web")?.value;
    usuari.notificacionsActives = this.formGroup.get("notificacions")?.value;
    usuari.bloquejat = false;
    usuari.idioma = this.idiomasList.find(idi => idi.nom == this.formGroup.get("idioma")?.value);
    usuari.rol = this.rolsList.find(ro => ro.nom == this.formGroup.get("rol")?.value);
    usuari.organ = this.organsList.find(org => org.nom == this.formGroup.get("organ")?.value);
    this.usuariService.register(usuari, this.isGoogleUser).subscribe(data => {
      this.retLogin = data;
      if (this.retLogin.loginCorrect) {
        localStorage.setItem('idUsuari', this.retLogin.idUsuari.toString());
        localStorage.setItem('token', this.retLogin.token);
        localStorage.setItem('codiRol', this.retLogin.codiRol);
        this.navCtrl.navigateRoot('/home');
      }
    });
  }
}
