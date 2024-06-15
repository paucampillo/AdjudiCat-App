import {Component, NgModule, OnInit} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { ReactiveFormsModule, FormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import {IonicModule, NavController} from '@ionic/angular';
import {RouterLink} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {addIcons} from "ionicons";
import {logoGoogle} from "ionicons/icons";
import {calcularHashSHA256} from "../../utils/hash-password";
import {ReturnLogin} from "../../model/return-login";
import { GoogleAuth } from '@codetrix-studio/capacitor-google-auth';
import {GooglePlus} from "@ionic-native/google-plus/ngx";




@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  providers: [GooglePlus],
  imports: [IonicModule, CommonModule, ReactiveFormsModule, FormsModule, RouterLink, NgOptimizedImage]
})
export class LoginPage implements OnInit {


  loginForm: FormGroup;
  missatgeError: string = '';
  retLogin: ReturnLogin;
  googleUser: any;


  constructor(
    private navCtrl: NavController,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private googlePlus: GooglePlus,
  ) {
    addIcons({logoGoogle});
  }

  ngOnInit(): void {
    this.loadFormGroup();
    this.clearLoginData();
  }

  loadFormGroup() {
    this.loginForm = this.formBuilder.group({
      correu: ['', [Validators.required, Validators.email]],
      contrasenya: ['', [Validators.required]]
    });
  }

  async clearLoginData() {
    localStorage.removeItem('idUsuari');
    localStorage.removeItem('token');
    localStorage.removeItem('codiRol');
    if (localStorage.getItem('googleEmail') || localStorage.getItem('googleName')) {
      localStorage.removeItem('googleEmail');
      localStorage.removeItem('googleName');
      try {
        this.googleUser = await GoogleAuth['signOut']();
      }
      catch (error) {
        console.error('Error al tancar sessió amb Google OAuth:', error);
      }
    }
    if (localStorage.getItem('googleCalendarToken')) {
      localStorage.removeItem('googleCalendarToken');
      try {
        this.googleUser = await GoogleAuth['signOut']();
      }
      catch (error) {
        console.error('Error al tancar sessió amb Google OAuth:', error);
      }
    }

  }

  iniciarSessio() {
    if (this.loginForm.valid) {
      const correu = this.loginForm.get('correu')?.value;
      const contrasenya = calcularHashSHA256(this.loginForm.get('contrasenya')?.value);
      const requestData = {
        email: correu,
        password: contrasenya
      };
      this.authService.iniciarSesio(requestData).subscribe(
        data => {
          this.retLogin = data;
          if (this.retLogin.loginCorrect) {
            localStorage.setItem('idUsuari', this.retLogin.idUsuari.toString());
            localStorage.setItem('token', this.retLogin.token);
            localStorage.setItem('codiRol', this.retLogin.codiRol);
            this.navCtrl.navigateRoot('/home');
          } else {
            this.missatgeError = this.retLogin.errorMsg;
          }
        }
      );
    } else {
      this.missatgeError = 'Omple correctament els camps';
    }
  }

  eliminarMissatgeError() {
    this.missatgeError = '';
  }


  async signInWithGoogle() {
    try {
      this.googleUser = await GoogleAuth['signIn']();

    } catch (error) {
      console.error('Error al iniciar sesión con Google OAuth:', error);
    }
    const requestData = {
      email: this.googleUser.email
    };

    this.authService.signInWithGoogle(requestData).subscribe((response: any) => {
        this.retLogin = response;
        if (this.retLogin.loginCorrect) {
            localStorage.setItem('idUsuari', this.retLogin.idUsuari.toString());
            localStorage.setItem('token', this.retLogin.token);
            localStorage.setItem('codiRol', this.retLogin.codiRol);
            localStorage.setItem('googleEmail', this.googleUser.email);
            localStorage.setItem('googleName', this.googleUser.name);
            this.navCtrl.navigateRoot('/home');
          }
         else {
          this.missatgeError = this.retLogin.errorMsg;
        }
      },
      (error: any) => {
        if (error.code === 404) {
          localStorage.setItem('googleEmail', this.googleUser.email);
          localStorage.setItem('googleName', this.googleUser.name);
          this.navCtrl.navigateRoot('/signup');
        }
        }
    );
  }
}


