import {Component, Input, OnInit} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {contract, star, starHalf} from "ionicons/icons";
import {addIcons} from "ionicons";
import {Usuari} from "../../model/usuari";
import {Contracte} from "../../model/contracte";
import {ContracteService} from "../../services/contracte.service";
import {NgForOf, NgIf} from "@angular/common";
import {FiltrarContracte} from "../../model/FiltrarContracte";
import {UsuariService} from "../../services/usuari.service";
import {TranslateModule, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-tarjeta-empresa',
  templateUrl: './tarjeta-empresa.component.html',
  styleUrls: ['./tarjeta-empresa.component.scss'],
  imports: [IonicModule, NgForOf, NgIf, TranslateModule],
  standalone: true
})
export class TarjetaEmpresaComponent  implements OnInit {

  @Input() contracte: Contracte;
  creador: Usuari;
  contractes: Contracte[];
  numLicitacions: number;
  numOpinions: number;
  valoracioMitjana = 0;
  idUsuari: number;
  usuari: Usuari;

  constructor(private contractesServei: ContracteService,
              private usuariServei: UsuariService,
              private translate: TranslateService) {
    addIcons({star});
    addIcons({starHalf});
  }

  ngOnInit() {
    this.creador = this.contracte.usuariCreacio;
    this.info();
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
      });
    }
  }

  info() {
    const paramsContracte = {
      idUsuariCreador : this.creador.idUsuari
    }

    this.contractes = [];
    this.contractesServei.findPaginatedCreador(paramsContracte).subscribe(data => {
      this.numLicitacions = data.totalElements;
      this.contractes = data.content;
    });
    this.usuariServei.getUsuari(this.creador.idUsuari).subscribe(data => {
      this.creador = data;
      this.valoracioMitjana = this.creador.valoracio;
      this.numOpinions = this.creador.numValoracions;
    });
  }

  get valoracioMitjanaEntera() {
    return new Array(Math.floor(this.valoracioMitjana));
  }

  get valoracioMitjanaDecimal() {
    return this.valoracioMitjana % 1;
  }
}
