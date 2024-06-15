import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";
import {MenuComponent} from "../menu/menu.component";
import {TarjetaLlistaComponent} from "../tarjeta-llista/tarjeta-llista.component";
import {ContracteService} from "../../services/contracte.service";
import {Contracte} from "../../model/contracte";
import {NavigationEnd, Router} from "@angular/router";
import {OfertaService} from "../../services/oferta.service";
import {Oferta} from "../../model/oferta";
import {FavoritsService} from "../../services/favorits.service";
import {filter} from "rxjs";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";
import {Usuari} from "../../model/usuari";

@Component({
  selector: 'app-licitacions',
  templateUrl: './licitacions.page.html',
  styleUrls: ['./licitacions.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, HeaderGenericoComponent, MenuComponent, TarjetaLlistaComponent, TranslateModule]
})
export class LicitacionsPage implements OnInit {
  codiRol: string;
  idUsuari: number;
  contractes: Contracte[];
  ofertes: Oferta[];
  favorits: Contracte[]
  usuari: Usuari;

  numLicitacionsP: number;
  numLicitacionsIO: number;
  numLicitacionsIT: number;
  numLicitacionsCO: number;
  numLicitacionsCT: number;

  creadesObertesMissatgeBuit: any;
  creadesObertesTitol: any;
  creadesTancadesMissatgeBuit: any;
  creadesTancadesTitol: any;
  inscritesObertesMissatgeBuit: any;
  inscritesObertesTitol: any;
  inscritesTancadesMissatgeBuit: any;
  inscritesTancadesTitol: any;
  preferidesMissatgeBuit: string;
  preferidesTitol: string;
  titol: string;

  constructor(private contractesServei: ContracteService,
              private ofertesServei: OfertaService,
              private favoritsServei: FavoritsService,
              private router: Router,
              private translate: TranslateService,
              private usuariServei: UsuariService) {
  }

  ngOnInit() {
    this.router.events.pipe(filter((event: any) => event instanceof NavigationEnd)).subscribe(() => {
      this.loadUsuari();
      this.numL()
    });
  }

  loadUsuari() {
    const codiRolStr = localStorage.getItem('codiRol');
    if (codiRolStr !== null) {
      this.codiRol = codiRolStr;
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
        this.translate.get('LICITACIONS.TITOL').subscribe((res: string) => {
          this.titol = res;
        });
        this.translate.get('LICITACIONS.PREFERIDES_TITOL').subscribe((res: string) => {
          this.preferidesTitol = res;
        });
        this.translate.get('LICITACIONS.PREFERIDES_MISSATGE_BUIT').subscribe((res: string) => {
          this.preferidesMissatgeBuit = res;
        });
        this.translate.get('LICITACIONS.CREADES_OBERTES_TITOL').subscribe((res: string) => {
          this.creadesObertesTitol = res;
        });
        this.translate.get('LICITACIONS.CREADES_OBERTES_MISSATGE_BUIT').subscribe((res: string) => {
          this.creadesObertesMissatgeBuit = res;
        });
        this.translate.get('LICITACIONS.CREADES_TANCADES_TITOL').subscribe((res: string) => {
          this.creadesTancadesTitol = res;
        });
        this.translate.get('LICITACIONS.CREADES_TANCADES_MISSATGE_BUIT').subscribe((res: string) => {
          this.creadesTancadesMissatgeBuit = res;
        });
        this.translate.get('LICITACIONS.INSCRITES_OBERTES_TITOL').subscribe((res: string) => {
          this.inscritesObertesTitol = res;
        });
        this.translate.get('LICITACIONS.INSCRITES_OBERTES_MISSATGE_BUIT').subscribe((res: string) => {
          this.inscritesObertesMissatgeBuit = res;
        });
        this.translate.get('LICITACIONS.INSCRITES_TANCADES_TITOL').subscribe((res: string) => {
          this.inscritesTancadesTitol = res;
        });
        this.translate.get('LICITACIONS.INSCRITES_TANCADES_MISSATGE_BUIT').subscribe((res: string) => {
          this.inscritesTancadesMissatgeBuit = res;
        });
      });
    }
  }

  numL() {
    const paramsFav = {
      id_user: this.idUsuari
    };
    this.favorits = [];
    this.favoritsServei.listarFavorits(paramsFav).subscribe(data => {
      this.favorits = data.content;
      this.numLicitacionsP = data.totalElements;
    });


    const paramsOfertes = {
      idUsuari : this.idUsuari
    }
    this.ofertes = [];
    this.ofertesServei.findOfertesByUsuari(paramsOfertes).subscribe(data => {
      this.ofertes = data.content
      this.numLicitacionsIO = data.totalElements;
    });

    this.ofertes = [];
    this.ofertesServei.findHistoricOfertesByUsuari(paramsOfertes).subscribe(data => {
      this.ofertes = data.content;
      this.numLicitacionsIT = data.totalElements;
    });


    const paramsContracte = {
      idUsuariCreador : this.idUsuari
    }
    this.contractes = [];
    this.contractesServei.findPaginatedCreador(paramsContracte).subscribe(data => {
      this.contractes = data.content;
      this.numLicitacionsCO = data.totalElements;
    });

    this.contractes = [];
    this.contractesServei.findPaginatedHistoricCreador(paramsContracte).subscribe(data => {
      this.contractes = data.content;
      this.numLicitacionsCT = data.totalElements;
    });
  }

  anarLicitacions(tipus: string) {
    switch (tipus) {
      case 'preferides':
        this.loadLicitacionsPreferides();
        break;
      case 'inscrites-obertes':
        this.loadLicitacionsInscritesObertes();
        break;
      case 'inscrites-tancades':
        this.loadLicitacionsInscritresTancades();
        break;
      case 'creades-obertes':
        this.loadLicitacionsCreadesObertes();
        break;
      case 'creades-tancades':
        this.loadLicitacionsCreadesTancades();
        break;
    }
  }


  private loadLicitacionsPreferides() {
    this.router.navigate(['/tipus-licitacions'], {
      queryParams: {
        titol: this.preferidesTitol,
        missatge: this.preferidesMissatgeBuit,
        idUsuari: this.idUsuari
      }
    });
  }


  loadLicitacionsInscritesObertes() {
    this.router.navigate(['/tipus-licitacions'], {
      queryParams: {
        titol: this.inscritesObertesTitol,
        missatge: this.inscritesObertesMissatgeBuit,
        idUsuari: this.idUsuari
      }
    });
  }


  loadLicitacionsInscritresTancades() {
    this.router.navigate(['/tipus-licitacions'], {
      queryParams: {
        titol: this.inscritesTancadesTitol,
        missatge: this.inscritesTancadesMissatgeBuit,
        idUsuari: this.idUsuari
      }
    });
  }


  loadLicitacionsCreadesObertes() {
    this.router.navigate(['/tipus-licitacions'], {
      queryParams: {
        titol: this.creadesObertesTitol,
        missatge: this.creadesObertesMissatgeBuit,
        idUsuari: this.idUsuari
      }
    });
  }


  loadLicitacionsCreadesTancades() {
    this.router.navigate(['/tipus-licitacions'], {
      queryParams: {
        titol: this.creadesTancadesTitol,
        missatge: this.creadesTancadesMissatgeBuit,
        idUsuari: this.idUsuari
      }
    });
  }
}
