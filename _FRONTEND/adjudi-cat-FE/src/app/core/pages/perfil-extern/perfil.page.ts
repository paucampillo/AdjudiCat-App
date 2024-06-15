import {Component, Input, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import {Contracte} from "../../model/contracte";
import {Usuari} from "../../model/usuari";
import {ContracteService} from "../../services/contracte.service";
import {ActivatedRoute, Router, RouterModule} from "@angular/router";
import {TarjetaPerfilComponent} from "../tarjeta-perfil/tarjeta-perfil.component";
import {TarjetaEmpresaComponent} from "../tarjeta-empresa/tarjeta-empresa.component";
import {TarjetaContracteComponent} from "../tarjeta-contracte/tarjeta-contracte.component";
import {FiltrarContracte} from "../../model/FiltrarContracte";
import {UsuariService} from "../../services/usuari.service";
import {TarjetaLlistaComponent} from "../tarjeta-llista/tarjeta-llista.component";
import {addIcons} from "ionicons";
import {checkmarkDoneOutline, closeCircleOutline, createOutline, heart, newspaperOutline, star} from "ionicons/icons";
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'app-perfil-extern',
  templateUrl: './perfil.page.html',
  styleUrls: ['./perfil.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, TarjetaEmpresaComponent, TarjetaPerfilComponent, RouterModule, TarjetaContracteComponent, TarjetaLlistaComponent, HeaderGenericoComponent, TranslateModule]
})
export class PerfilPage implements OnInit {

  licitacionsList: Contracte[] = [];
  filtreContracte: FiltrarContracte;
  contracte: Contracte;
  idUsuari: number;
  codiRol: string;
  idOrgan: number;
  organ: any;

  page: number = 1;
  totalPages: number = 1;

  missatgeResultatFiltreBuit = '';
  private usuari: any;

  constructor(private route: ActivatedRoute, private router: Router, private usuariServei: UsuariService, private contracteService: ContracteService, private translate: TranslateService) {
    addIcons({createOutline});
    addIcons({closeCircleOutline});
    addIcons({star});
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.idOrgan = params['idUsuari'];
    });
    this.loadUsuari();
    this.loadContractes(null,false);
    this.loadOrgan();
  }

  loadUsuari() {
    const idUsuariStr = localStorage.getItem('idUsuari');
    const codiRolStr = localStorage.getItem('codiRol');
    if (codiRolStr !== null) {
      this.codiRol = codiRolStr;
    }
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
        this.translate.get('PERFIL_EXTERN').subscribe((res: any) => {
          this.loadText = res;
        });
        this.translate.get('PERFIL_EXTERN.FILTRE_BUIT').subscribe((res: any) => {
          this.missatgeResultatFiltreBuit = res;
        });
      });
    }
  }

  loadOrgan() {
    this.usuariServei.getUsuari(this.idOrgan).subscribe((data: Usuari) => {
      this.organ = data;
    });
  }

  //filtrar per usuari
  loadContractes($event: any, next: boolean) {
    if (next) {
      this.page += 1;
    }
    const params = {
      page: this.page,
      idUsuariCreador : this.idOrgan,
    }
    this.contracteService.findPaginatedCreador(params).subscribe(data => {
      this.totalPages = data.totalPages;
      this.licitacionsList = [...this.licitacionsList, ...data.content];
      if ($event) {
        $event.target.complete();
      }
    });
  }

  anarDetalls(contracte: Contracte) {
    this.router.navigate(['/contracte', contracte.idContracte]);
  }

  enviarMissatge(usuari: Usuari) {
    this.router.navigate(['/chat-mensajes'+'/'+this.idUsuari+'/'+this.idOrgan]);
    //this.router.navigate(['/chat-mensajes/',this.idUsuari, this.idOrgan]);//this.contracte.usuariCreacio?.idUsuari]);
  }

  protected readonly Usuari = Usuari;
  loadText: any;

  navegaValoracionsOrgan() {
    let tipusVal = 'organ';
    this.router.navigate(['/valoracions', tipusVal, this.idOrgan]);
  }
}
