import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {addIcons} from "ionicons";
import {Contracte} from "../../model/contracte";
import {heart, heartOutline} from "ionicons/icons";
import {FavoritsService} from "../../services/favorits.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ReturnFavoritsDTO} from "../../model/ReturnFavoritsDTO";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";
import {Usuari} from "../../model/usuari";

@Component({
  selector: 'app-tarjeta-contracte',
  templateUrl: './tarjeta-contracte.component.html',
  styleUrls: ['./tarjeta-contracte.component.scss'],
  imports: [IonicModule, TranslateModule],
  standalone: true,
})
export class TarjetaContracteComponent  implements OnInit {
  @Input() contracte: Contracte;
  idUsuari: number;
  usuari: Usuari;
  fav: ReturnFavoritsDTO;
  @Output() favorit = new EventEmitter<boolean>();

  constructor(private route: ActivatedRoute,
              private favoritsService: FavoritsService,
              private translate: TranslateService,
              private usuariServei: UsuariService) {
    addIcons({heartOutline});
    addIcons({heart});
  }

  ngOnInit() {
    this.loadUsuari();
    this.fav = new ReturnFavoritsDTO();
    this.fav.idContracte = this.contracte.idContracte;
    this.fav.idUsuari = this.idUsuari;
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

  canviFavorit(event: Event) {
    event.stopPropagation();
    if (this.contracte.preferit) {
      this.deleteFavorit()
    }
    else {
      this.addFavorit();
    }
  }

  addFavorit() {
    this.favoritsService.addFavorit(this.fav).subscribe(() => {
      this.contracte.preferit = true;
      this.favorit.emit(true);
    });
  }

  deleteFavorit() {
    this.favoritsService.removeFavorit(this.fav).subscribe(() => {
      this.contracte.preferit = false;
      this.favorit.emit(false);
    });
  }
}
