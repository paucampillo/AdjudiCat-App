import {Component, Input, OnInit} from '@angular/core';
import {checkmarkDoneOutline, closeCircleOutline, createOutline, heart, newspaperOutline} from "ionicons/icons";
import {Router} from "@angular/router";
import {addIcons} from "ionicons";
import {IonicModule} from "@ionic/angular";
import {NgIf} from "@angular/common";
import {Usuari} from "../../model/usuari";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";

@Component({
  selector: 'app-tarjeta-llista',
  templateUrl: './tarjeta-llista.component.html',
  styleUrls: ['./tarjeta-llista.component.scss'],
  imports: [IonicModule, NgIf, TranslateModule],
  standalone: true
})
export class TarjetaLlistaComponent  implements OnInit {
  @Input() tipus: number;
  @Input() numLicitacions: number;

  nom: string;
  icono: string;

  idUsuari: number;
  usuari: Usuari;

  nomPreferides: string;
  inscritesObertes: string;
  inscritesTancades: string;
  creadesObertes: string;
  creadesTancades: string;

  constructor(private router: Router,
              private translate: TranslateService,
              private usuariServei: UsuariService) {
    addIcons({newspaperOutline});
    addIcons({heart});
    addIcons({checkmarkDoneOutline});
    addIcons({createOutline});
    addIcons({closeCircleOutline});
  }

  ngOnInit() {
    this.loadUsuari();
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

        this.translate.get('LICITACIONS.PREFERIDES_TITOL').subscribe((res: string) => {
          this.nomPreferides = res;
          if (this.tipus === 1) {
            this.nom = this.nomPreferides;
            this.icono = 'heart';
          }
        });
        this.translate.get('LICITACIONS.INSCRITES_OBERTES_TITOL').subscribe((res: string) => {
          this.inscritesObertes = res;
          if (this.tipus === 2) {
            this.nom = this.inscritesObertes;
            this.icono = 'newspaper-outline';
          }
        });
        this.translate.get('LICITACIONS.INSCRITES_TANCADES_TITOL').subscribe((res: string) => {
          this.inscritesTancades = res;
          if (this.tipus === 3) {
            this.nom = this.inscritesTancades;
            this.icono = 'checkmark-done-outline';
          }
        });
        this.translate.get('LICITACIONS.CREADES_OBERTES_TITOL').subscribe((res: string) => {
          this.creadesObertes = res;
          if (this.tipus === 4) {
            this.nom = this.creadesObertes;
            this.icono = 'create-outline';
          }
        });
        this.translate.get('LICITACIONS.CREADES_TANCADES_TITOL').subscribe((res: string) => {
          this.creadesTancades = res;
          if (this.tipus === 5) {
            this.nom = this.creadesTancades;
            this.icono = 'close-circle-outline';
          }
        });
      });
    }
  }
}


