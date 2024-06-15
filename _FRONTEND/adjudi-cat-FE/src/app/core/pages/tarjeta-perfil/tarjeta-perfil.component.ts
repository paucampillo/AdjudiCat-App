import {Component, Input, OnInit} from '@angular/core';
import {Usuari} from "../../model/usuari";
import {IonicModule} from "@ionic/angular";
import {NgIf} from "@angular/common";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";

@Component({
  selector: 'app-tarjeta-perfil-extern',
  templateUrl: './tarjeta-perfil.component.html',
  styleUrls: ['./tarjeta-perfil.component.scss'],
  imports: [IonicModule, NgIf, TranslateModule],
  standalone: true
})
export class TarjetaPerfilComponent  implements OnInit {
  @Input() usuari: Usuari;

  idUsuari: number;
  usuariConta: Usuari;

  constructor(private translate: TranslateService,
              private usuariServei: UsuariService) { }

  ngOnInit(): void {
    this.loadUsuari();
  }

  loadUsuari() {
    const idUsuariStr = localStorage.getItem('idUsuari');
    if (idUsuariStr !== null) {
      this.idUsuari = parseInt(idUsuariStr, 10);
      this.usuariServei.getUsuari(this.idUsuari).subscribe(data => {
        this.usuariConta = data;
        if (this.usuariConta.idioma?.codi === 'ENG') {
          this.translate.use('en');
        } else if (this.usuariConta.idioma?.codi === 'CAST') {
          this.translate.use('es');
        } else {
          this.translate.use('ca');
        }
      });
    }
  }
}
