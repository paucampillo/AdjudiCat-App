import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {MenuComponent} from '../menu/menu.component'
import {IonicModule, ModalController} from "@ionic/angular";
import {addIcons} from "ionicons";
import {TarjetaContracteComponent} from "../tarjeta-contracte/tarjeta-contracte.component";
import {ContracteService} from "../../services/contracte.service";
import {Contracte} from "../../model/contracte";
import {NgForOf, NgIf} from "@angular/common";
import {FiltrarContracte} from "../../model/FiltrarContracte";
import {NavigationEnd, Router, RouterLink} from "@angular/router";
import {FiltrosComponent} from "./filtros/filtros.component";
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";
import {addOutline, options, locationOutline, locate, checkmark} from "ionicons/icons";
import {GooglemapsComponent} from "../../googlemaps/googlemaps.component";
import {Usuari} from "../../model/usuari";
import {FormControl, FormGroup, FormsModule} from "@angular/forms";
import {filter} from "rxjs";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../services/usuari.service";

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
  standalone: true,
  providers: [TranslateService],
  imports: [MenuComponent, IonicModule, TarjetaContracteComponent, NgForOf, RouterLink,
    FiltrosComponent, NgIf, HeaderGenericoComponent, FormsModule, TranslateModule],
})

export class HomePage implements OnInit {

  usuari: Usuari = {
    idUsuari: 0,
    nom: '',
    email: '',
    contrasenya: '',
    pais: '',
    codiPostal: 0,
    direccio: '',
    telefon: '',
    enllacPerfilSocial: '',
    notificacionsActives: false,
    identNif: '',
    descripcio: '',
    bloquejat: false,
    ubicacio: {
      lat: 0,
      lng: 0
    },
    valoracio: 0,
    numValoracions: 0
  };


  contractesList: Contracte[] = [];
  filtreContracte: FiltrarContracte;
  codiRol: string;
  searchTerm: string = '';
  sort = 'codiExpedient';
  idUsuari: number;
  page = 1;
  totalPages: number;

  buscar: string;
  titulo: string;

  constructor(private contractesServei: ContracteService,
              private router: Router,
              public modalController: ModalController,
              private translate: TranslateService,
              private usuariServei: UsuariService) {
    addIcons({options});
    addIcons({addOutline});
    addIcons({locationOutline});
    addIcons({checkmark});
    addIcons({locate});
  }

  ngOnInit() {
    this.router.events.pipe(filter((event: any) => event instanceof NavigationEnd)).subscribe(() => {
      this.loadFiltres();
      this.loadUsuari();
      this.loadContractes();
    });
  }

  loadContractes() {
    const params = {
      page: this.page,
      sort: this.sort,
      idUsuari : this.idUsuari
    };
    this.contractesServei.findPaginated(this.filtreContracte, params).subscribe(data => {
      this.totalPages = data.totalPages;
      this.contractesList = data.content;
    })
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
        this.translate.get('HOME.TITOL').subscribe((res: string) => {
          this.titulo = res;
        });
        this.translate.get('HOME.BUSCAR').subscribe((res: string) => {
          this.buscar = res;
        });
      });
    }
  }

  loadFiltres() {
    this.filtreContracte = new FiltrarContracte();
  }

  loadContractesAfterFilter($event: any) {
    this.filtreContracte.codiExpedient = $event.nouCodi;
    this.filtreContracte.tipusContracte = $event.tipusContracteSeleccionat;
    this.filtreContracte.ambit = $event.tipusAmbitSeleccionat;
    this.filtreContracte.procediment = $event.tipusProcedimetSeleccionat;
    this.filtreContracte.llocExecucio = $event.nouLloc;
    this.filtreContracte.valorContracte = $event.nouValor;
    this.filtreContracte.objecteContracte = $event.nouObjecte;
    this.page = 1;
    const params = {
      page: this.page,
      sort: $event.sort,
      idUsuari: this.idUsuari
    }
    this.contractesServei.findPaginatedAndSorted(this.filtreContracte, params).subscribe(data => {
      this.totalPages = data.totalPages;
      this.contractesList = data.content
    });
  }

  anarDetalls(contracte: Contracte) {
    this.router.navigate(['/contracte', contracte.idContracte]);
  }

  pujarContracte() {
    this.router.navigate(['/publicar-contracte']);
  }

  filterContractesBuscador() {
    if (this.filtreContracte === undefined) {
      this.filtreContracte = new FiltrarContracte();
    }
    this.filtreContracte.objecteContracte = this.searchTerm;
    this.page = 1;
    const params = {
      page: this.page,
      sort: this.sort,
      idUsuari: this.idUsuari
    }
    this.contractesServei.findPaginatedAndSorted(this.filtreContracte, params).subscribe(data => {
      this.totalPages = data.totalPages;
      this.contractesList = data.content;
    });
  }

  onSearchTermChange(newSearchTerm: string) {
    this.searchTerm = newSearchTerm;
  }

  async buscarMaps() {
    const ubicacion = this.usuari.ubicacio;
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(async (position) => {
        this.usuari.ubicacio = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };

        /*
        let positionInput: { lat: number; lng: number } | undefined = {
          lat: 41.716487,
          lng: 2.547796
        };
        /*
        if (ubicacion !== null) {
          positionInput = ubicacion;
        }
         */
        const modalAdd = await this.modalController.create({
          component: GooglemapsComponent,
          //swipeToClose: true,
          componentProps: {position: this.usuari.ubicacio, userLocation: this.usuari.ubicacio}
        });
        await modalAdd.present();
        const {data} = await modalAdd.onWillDismiss();
        if (data) {
          this.usuari.ubicacio = data.pos;
        }
      });
    } else {
    }
  }

  loadMoreContractes($event: any) {
    this.page += 1;
    const params = {
      page: this.page,
      sort: this.sort,
      idUsuari: this.idUsuari
    }
    this.contractesServei.findPaginatedAndSorted(this.filtreContracte, params).subscribe(data => {
      this.contractesList = [...this.contractesList, ...data.content];
      $event.target.complete();
    });

  }
}
