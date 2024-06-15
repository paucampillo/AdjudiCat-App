import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {AlertController, IonicModule} from '@ionic/angular';
import {MenuComponent} from "../../menu/menu.component";
import {TarjetaEmpresaComponent} from '../../tarjeta-empresa/tarjeta-empresa.component';
import {ActivatedRoute, Router, RouterModule} from "@angular/router";
import {Contracte} from "../../../model/contracte";
import {addIcons} from "ionicons";
import {ContracteService} from "../../../services/contracte.service";
import {heart, heartOutline, trash} from "ionicons/icons";
import {FavoritsService} from "../../../services/favorits.service";
import {ReturnFavoritsDTO} from "../../../model/ReturnFavoritsDTO";
import {FiltrosComponent} from "../filtros/filtros.component";
import {ValoraEmpresaComponent} from "./valora-empresa/valora-empresa.component";
import {HeaderGenericoComponent} from "../../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../../services/usuari.service";
import {Usuari} from "../../../model/usuari";

@Component({
  selector: 'app-contracte',
  templateUrl: './contracte.page.html',
  styleUrls: ['./contracte.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, MenuComponent, TarjetaEmpresaComponent, RouterModule, FiltrosComponent, ValoraEmpresaComponent, HeaderGenericoComponent, TranslateModule]
})
export class ContractePage implements OnInit {
  contracte: Contracte;
  idUsuari: number;
  codiRol: string;

  usuari: Usuari;

  fav: ReturnFavoritsDTO = new ReturnFavoritsDTO();

  cancelar: string;
  confirmacio: string;
  confirmar: string;
  esborrar: string;
  missatgeConfiramcioEsborrar: string;
  missatgePreguntaConfirmarEsborrar: string;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private contracteService: ContracteService,
              private favoritsService: FavoritsService,
              private alertController: AlertController,
              private translate: TranslateService,
              private usuariServei: UsuariService) {
    addIcons({trash});
    addIcons({heart});
    addIcons({heartOutline});
  }

  ngOnInit() {
    this.loadUsuari();
    this.loadContracte();
  }

  loadContracte() {
    this.route.params.subscribe(params => {
      const id = params['idContracte'];
      this.contracteService.findContracteId(id, this.idUsuari).subscribe(data => {
        this.contracte = data;
      })
    });
  }

  loadUsuari() {
    const codiRolstr = localStorage.getItem('codiRol');
    if (codiRolstr != null) {
      this.codiRol = codiRolstr;
    }
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
        this.translate.get('COMUNS.CONFIRMAR').subscribe((res: string) => {
          this.confirmar = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.PREGUNTA_CONFIRMAR_ESBORRAR_CONTRACTE').subscribe((res: string) => {
          this.missatgePreguntaConfirmarEsborrar = res;
        });
        this.translate.get('COMUNS.CANCELAR').subscribe((res: string) => {
          this.cancelar = res;
        });
        this.translate.get('COMUNS.ESBORRA').subscribe((res: string) => {
          this.esborrar = res;
        });
        this.translate.get('COMUNS.CONFIRMACIÓ').subscribe((res: string) => {
          this.confirmacio = res;
        });
        this.translate.get('MISSATGES_CONFIRMACIONS.CONFIRMACIO_ESBORRAR_CONTRACTE').subscribe((res: string) => {
          this.missatgeConfiramcioEsborrar = res;
        });
      });
    }
  }

  licitar(contracte: Contracte) {
    this.router.navigate(['/contracte', contracte.idContracte, 'licitar'], {state: {objecte: contracte}});
  }

  async onDelete() {
    const confirm = await this.mostrarConfirmacioEsborrar();
    if (confirm) {
      this.contracteService.eliminar(this.contracte.idContracte).subscribe(() => {
        this.router.navigate(['/home']);
        this.mostrarMissatgeContracteEsborrat();
      })
    }
  }

  async mostrarConfirmacioEsborrar() {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header: this.confirmar,
        message: this.missatgePreguntaConfirmarEsborrar,
        buttons: [
          {
            text: this.cancelar,
            role: 'cancel',
            cssClass: 'secondary',
            handler: () => {
              resolve(false);
            }
          }, {
            text: this.esborrar,
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      await alert.present();
    });
  }

  async mostrarMissatgeContracteEsborrat() {
    const alert = await this.alertController.create({
      header: this.confirmacio,
      message: this.missatgeConfiramcioEsborrar,
      buttons: ['OK']
    });
    await alert.present();
  }

  canviFavorit() {
    if (this.contracte.preferit) {
      this.deleteFavorit()
    }
    else {
      this.addFavorit();
    }
  }

  addFavorit() {
    this.fav.idContracte = this.contracte.idContracte;
    this.fav.idUsuari = this.idUsuari;
    this.favoritsService.addFavorit(this.fav).subscribe(() => this.contracte.preferit = true);
  }

  deleteFavorit() {
    this.fav.idContracte = this.contracte.idContracte;
    this.fav.idUsuari = this.idUsuari;
    this.favoritsService.removeFavorit(this.fav).subscribe(() => this.contracte.preferit = false);
  }

  viewPerfilExtern() {
    this.router.navigate(['/perfil-extern-organ', this.contracte.usuariCreacio?.idUsuari]);
  }
}
