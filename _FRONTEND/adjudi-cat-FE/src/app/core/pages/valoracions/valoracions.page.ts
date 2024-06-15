import {Component, Input, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import {ActivatedRoute} from "@angular/router";
import {ValoracionsService} from "../../services/valoracions.service";
import {ValoracioDTO} from "../../model/ValoracioDTO";
import {TarjetaValoracionsComponent} from "../tarjeta-valoracions/tarjeta-valoracions.component";
import {TarjetaContracteComponent} from "../tarjeta-contracte/tarjeta-contracte.component";
import {addIcons} from "ionicons";
import {star} from "ionicons/icons";
import {UsuariService} from "../../services/usuari.service";
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";
import {Usuari} from "../../model/usuari";
import {TranslateModule, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-valoracions',
  templateUrl: './valoracions.page.html',
  styleUrls: ['./valoracions.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, TarjetaValoracionsComponent, TarjetaContracteComponent, HeaderGenericoComponent, TranslateModule]
})
export class ValoracionsPage implements OnInit {
  @Input() tipusVal: string
  titol: string
  idUsuari: number;
  valoracions: ValoracioDTO[] = [];
  missatgeNoValoracions: string;
  puntuacioMitjana: number;
  idOrgan: number;
  page = 1;
  totalPages = 1;
  usuari: Usuari;
  organ: Usuari;

  missatgeNoValoracionsOrgan: string;
  missatgeNoValoracionsRealitzades: string;
  missatgeNoValoracionsRebudes: string;
  titolRealitzades: string;
  titolRebudes: string;

  constructor(private route: ActivatedRoute,
              private valoracionsServei: ValoracionsService,
              private usuariService: UsuariService,
              private translate: TranslateService) {
    addIcons({star});
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.tipusVal = params['tipusVal'];
      if (this.tipusVal == 'organ') {
        this.idOrgan = params['idOrgan'];
      }
      this.loadUsuari(this.tipusVal);
    });
  }

  loadUsuari(tipusVal: string) {
    const idUsuariStr = localStorage.getItem('idUsuari');
    if (idUsuariStr !== null) {
      this.idUsuari = parseInt(idUsuariStr, 10);
      this.usuariService.getUsuari(this.idUsuari).subscribe(data => {
        this.usuari = data;
        this.puntuacioMitjana = this.usuari.valoracio;

        if (this.usuari.idioma?.codi === 'ENG') {
          this.translate.use('en');
        } else if (this.usuari.idioma?.codi === 'CAST') {
          this.translate.use('es');
        } else {
          this.translate.use('ca');
        }
        this.translate.get('VALORACIONS.TITOL_REBUDES').subscribe((res: string) => {
          this.titolRebudes = res;
        });
        this.translate.get('VALORACIONS.TITOL_REALITZADES').subscribe((res: string) => {
          this.titolRealitzades = res;
        });
        this.translate.get('VALORACIONS.MISSATGE_NO_VALORACIONS_REBUDES').subscribe((res: string) => {
          this.missatgeNoValoracionsRebudes = res;
        });
        this.translate.get('VALORACIONS.MISSATGE_NO_VALORACIONS_REALITZADES').subscribe((res: string) => {
          this.missatgeNoValoracionsRealitzades = res;
        });
        this.translate.get('VALORACIONS.MISSATGE_NO_VALORACIONS_ORGAN').subscribe((res: string) => {
          this.missatgeNoValoracionsOrgan = res;
        });

        this.loadValoracions(null, false);
        console.log(this.titol)
      });
    }
  }

  loadValoracions($event: any, next: boolean) {
    this.route.params.subscribe(params => {
      let tipusVal = params['tipusVal'];

      if (next) {
        this.page++;
      }
      const params2 = {
        page: this.page,
        idUsuari: this.idUsuari
      }
      const params3 = {
        page: this.page,
        idUsuari: this.idOrgan
      }
      if (tipusVal == "rebudes") {
        this.titol = this.titolRebudes;
        this.missatgeNoValoracions = this.missatgeNoValoracionsRebudes;
        this.valoracionsServei.listValoracionsReceptor(params2).subscribe((valoracions: any) => {
          this.totalPages = valoracions.totalPages;
          this.valoracions = [...this.valoracions, ...valoracions.content];
          if (this.usuari) {
            this.puntuacioMitjana = this.usuari.valoracio;
          }
          if ($event) {
            $event.target.complete();
          }
        });
      } else if (tipusVal == "realitzades") {
        this.titol = this.titolRealitzades;
        this.missatgeNoValoracions = this.missatgeNoValoracionsRealitzades;
        this.valoracionsServei.listValoracionsEmisor(params2).subscribe((valoracions: any) => {
          this.totalPages = valoracions.totalPages;
          this.valoracions = [...this.valoracions, ...valoracions.content];
          if (this.usuari) {
            this.puntuacioMitjana = this.usuari.valoracio;
          }
          if ($event) {
            $event.target.complete();
          }
        });
      }
      else if (tipusVal == "organ") {
        this.titol = this.titolRebudes;
        this.missatgeNoValoracions = this.missatgeNoValoracionsOrgan;
        this.valoracionsServei.listValoracionsReceptor(params3).subscribe((valoracions: any) => {
          this.totalPages = valoracions.totalPages;
          this.valoracions = [...this.valoracions, ...valoracions.content];
          this.usuariService.getUsuari(this.idOrgan).subscribe(data => {
            this.organ = data;
            if (this.organ) {
              this.puntuacioMitjana = this.organ.valoracio;
            }
          });
          if ($event) {
            $event.target.complete();
          }
        });
      }
    });
  }

  get valoracioMitjanaEntera() {
    return new Array(Math.floor(this.puntuacioMitjana));
  }

  get valoracioMitjanaDecimal() {
    return this.puntuacioMitjana % 1;
  }

  reloadValoracions() {
    this.valoracions = [];
    this.page = 1;
    this.loadValoracions(null, false);
  }
}
