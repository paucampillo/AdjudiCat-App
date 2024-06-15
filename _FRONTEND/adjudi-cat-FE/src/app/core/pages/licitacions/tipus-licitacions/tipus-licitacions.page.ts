import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import {TarjetaContracteComponent} from "../../tarjeta-contracte/tarjeta-contracte.component";
import {Contracte} from "../../../model/contracte";
import {ActivatedRoute, NavigationEnd, Router} from "@angular/router";
import {FavoritsService} from "../../../services/favorits.service";
import {OfertaService} from "../../../services/oferta.service";
import {ContracteService} from "../../../services/contracte.service";
import {HeaderGenericoComponent} from "../../ComponentesCabecera/header-generico/header-generico.component";
import {filter} from "rxjs";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../../services/usuari.service";
import {Usuari} from "../../../model/usuari";

@Component({
  selector: 'app-tipus-licitacions',
  templateUrl: './tipus-licitacions.page.html',
  styleUrls: ['./tipus-licitacions.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, TarjetaContracteComponent, HeaderGenericoComponent, TranslateModule]
})

export class TipusLicitacionsPage implements OnInit {
  titulo: string;
  licitacionsList: Contracte[] = [];
  missatgeBuit: string;
  idUsuari: number;
  page = 1;
  totalPages = 1;
  usuari: Usuari;

  carregantContractes: string;
  preferidesTitol: string;
  creadesObertesTitol: string;
  creadesTancadesTitol: string;
  inscritesObertesTitol: string;
  inscritesTancadesTitol: string;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private favoritsService: FavoritsService,
              private ofertaService: OfertaService,
              private contracteService: ContracteService,
              private translate: TranslateService,
              private usuariServei: UsuariService) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.titulo = params['titol'];
      this.missatgeBuit = params['missatge'];
      this.idUsuari = params['idUsuari'];
    });
    this.router.events.pipe(filter((event: any) => event instanceof NavigationEnd)).subscribe(() => {
      this.licitacionsList = [];
      this.loadUsuari();
    });
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
        this.translate.get('COMUNS.CARREGANT_CONTRACTES').subscribe((res: string) => {
          this.carregantContractes = res;
        });
        this.translate.get('LICITACIONS.PREFERIDES_TITOL').subscribe((res: string) => {
          this.preferidesTitol = res;
        });
        this.translate.get('LICITACIONS.CREADES_OBERTES_TITOL').subscribe((res: string) => {
          this.creadesObertesTitol = res;
        });
        this.translate.get('LICITACIONS.CREADES_TANCADES_TITOL').subscribe((res: string) => {
          this.creadesTancadesTitol = res;
        });
        this.translate.get('LICITACIONS.INSCRITES_OBERTES_TITOL').subscribe((res: string) => {
          this.inscritesObertesTitol = res;
        });
        this.translate.get('LICITACIONS.INSCRITES_TANCADES_TITOL').subscribe((res: string) => {
          this.inscritesTancadesTitol = res;
        });

        this.loadContractes(null, false);
      });
    }
  }

  loadContractes($event: any, next: boolean) {
    switch (this.titulo) {
      case this.preferidesTitol:
        this.loadPreferides($event, next);
        break;
      case this.inscritesObertesTitol:
        this.loadInscritesObertes($event, next);
        break;
      case this.inscritesTancadesTitol:
        this.loadInscritesTancades($event, next);
        break;
      case this.creadesObertesTitol:
        this.loadCreadesObertes($event, next);
        break;
      case this.creadesTancadesTitol:
        this.loadCreadesTancades($event, next);
        break;
    }
  }

  loadPreferides($event: any, next: boolean) {
    if (next) {
      this.page += 1;
    }
    const params = {
      page: this.page,
      id_user: this.idUsuari
    };
    this.favoritsService.listarFavorits(params).subscribe(data => {
      this.totalPages = data.totalPages;
      this.licitacionsList = [...this.licitacionsList, ...data.content];
      if ($event) {
        $event.target.complete();
      }
    });
  }

  loadInscritesObertes($event: any, next: boolean) {
    if (next) {
      this.page += 1;
    }
    const params = {
      page: this.page,
      idUsuari : this.idUsuari
    }
    this.ofertaService.findOfertesByUsuari(params).subscribe(data => {
      this.totalPages = data.totalPages;
      this.licitacionsList = [...this.licitacionsList, ...data.content];
      if ($event) {
        $event.target.complete();
      }
    });
  }

  loadInscritesTancades($event: any, next: boolean) {
    if (next) {
      this.page += 1;
    }
    const params = {
      page: this.page,
      idUsuari : this.idUsuari
    }
    this.ofertaService.findHistoricOfertesByUsuari(params).subscribe(data => {
      this.totalPages = data.totalPages;
      this.licitacionsList = [...this.licitacionsList, ...data.content];
      if ($event) {
        $event.target.complete();
      }
    });
  }

  loadCreadesObertes($event: any, next: boolean) {
    if (next) {
      this.page += 1;
    }
    const params = {
      page: this.page,
      idUsuariCreador : this.idUsuari
    }
    this.contracteService.findPaginatedCreador(params).subscribe(data => {
      this.totalPages = data.totalPages;
      this.licitacionsList = [...this.licitacionsList, ...data.content];
      if ($event) {
        $event.target.complete();
      }
    });
  }

  loadCreadesTancades($event: any, next: boolean) {
    if (next) {
      this.page += 1;
    }
    const params = {
      page: this.page,
      idUsuariCreador : this.idUsuari
    }
    this.contracteService.findPaginatedHistoricCreador(params).subscribe(data => {
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

  reloadContractes() {
    if (this.titulo === this.preferidesTitol) {
      this.page = 1;
      this.licitacionsList = [];
      this.loadPreferides(null, false);
    }
  }
}
