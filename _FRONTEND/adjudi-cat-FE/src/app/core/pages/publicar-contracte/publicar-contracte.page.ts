import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import {AlertController, IonicModule, NavController} from '@ionic/angular';
import {addIcons} from "ionicons";
import {Contracte} from "../../model/contracte";
import {searchOutline} from "ionicons/icons";
import {AmbitService} from "../../services/ambit.service";
import {LotService} from "../../services/lot.service";
import {calcularHashSHA256} from "../../utils/hash-password";
import {Ambit} from "../../model/ambit";
import {Lot} from "../../model/lot";
import {Usuari} from "../../model/usuari";
import {UsuariService} from "../../services/usuari.service";
import {ContracteService} from "../../services/contracte.service";
import {Router} from "@angular/router";
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";
import {TranslateModule, TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-publicar-contracte',
  templateUrl: './publicar-contracte.page.html',
  styleUrls: ['./publicar-contracte.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, ReactiveFormsModule, HeaderGenericoComponent, TranslateModule]
})

export class PublicarContractePage implements OnInit {

  public formGroup: FormGroup;
  TContracteList: String[] = ["Obres", "Subministraments", "Serveis", "Privat d\'Administració Pública",
    "Gestió de Serveis Públics", "Altra legislació sectorial",
    "Concessió de serveis", "Tipo1"];

  STContracteListO: String[] = ["Construcció general d'immobles i obres d'enginyeria civil",
    "Altres acabats d'edificis i obres",
    "Construcció",
    "Pintura i envidriament",
    "Altres instal·lacions d'edificis i obres",
    "Altres construccions especialitzades",
    "Instal·lació elèctrica",
    "Construcció d'autopistes, carreteres, camps d'aterratge, vies fèrries i centres esportius",
    "Perforacions i sondejos",
    "Instal·lació d'edificis i obres",
    "Construcció general d'edificis i obres singulars d'enginyeria civil (ponts, túnels, etc.)",
    "Acabat d'edificis i obres",
    "Construcció de cobertes i estructures de tancament",
    "Revestiment de sòls i parets",
    "Instal·lacions de fusteria",
    "Obres hidràuliques"];

  STContracteListSubm: String[] = ["Adquisició", "Lloguer", "Altres serveis"];

  STContracteListServ: String[] = ["Serveis de manteniment i reparació",
    "Serveis de telecomunicació",
    "Serveis de transport per via terrestre, inclosos els serveis de furgons blindats i serveis de missatgeria, excepte el transport de correu",
    "Serveis socials i de salut",
    "Serveis d'esplai, culturals i esportius",
    "Serveis d'investigació d'estudis i enquestes de l'opinió pública",
    "Altres serveis",
    "Serveis d'educació i formació professional",
    "Serveis de comptabilitat, auditoria i tenidoria de llibres",
    "Serveis d'arquitectura, serveis d'enginyeria i serveis integrats d'enginyeria, serveis de planificació urbana i serveis d'arquitectura paisatgista. Serveis connexos de consultors en ciència i tecnologia. Serveis d'assaigs i anàlisis tècnics",
    "Serveis d'informàtica i serveis connexos",
    "Serveis de clavegueram i eliminació de deixalles: serveis de sanejament i serveis similars",
    "Serveis editorials i d'impremta, per tarifa o per contracte",
    "Serveis de neteja d'edificis i serveis d'administració de béns arrels",
    "Serveis financers: a) serveis d'assegurances, b) serveis bancaris i d'inversió",
    "Serveis d'investigació i desenvolupament",
    "Serveis d'hostaleria i restaurant",
    "Serveis d'investigació i seguretat, excepte els serveis de furgons blindats",
    "Transport de correu per via terrestre i per via aèria",
    "Serveis de consultors de direcció i serveis connexos",
    "serveis jurídics",
    "Serveis de publicitat"];

  STContracteListPAP: String[] = ["Altres contractes patrimonials"];


  STContracteListALS: String[] = ["Arrendament d'immobles", "Concessió demanial"];


  STContracteListT1: String[] = ["SubtipoA"];

  STContracteList: String[] = [];

  TProcedimentList: String[] = ["Contracte menor",
    "Obert",
    "Contracte basat en acord marc",
    "Negociat sense publicitat",
    "Restringit",
    "Concurs de projectes",
    "Licitació amb negociació",
    "Altres procediments segons instruccions internes",
    "Abierto"];

  TResultatList: String[] = ["Adjudicat",
    "Formalitzat",
    "Desert",
    "Adjudicado"];

  ambitList: Ambit[] = [];
  lotList: Lot[] = [];
  usuari: Usuari;
  codiRol: string;
  minDate: string;

  accepta: string;
  ambit: string;
  anys: string;
  cancelar: string;
  codiExpedient: string;
  dies: string;
  introdueixCodi: string;
  introdueixLlocExecucio: string;
  introdueixObjecteContracte: string;
  introdueixPressupost: string;
  introdueixURL: string;
  introdueixValorEstimat: string;
  llocExecucio: string;
  lots: string;
  mesos: string;
  nomLot: string;
  nomSubtipusContracte: string;
  numAnys: string;
  numDies: string;
  numMesos: string;
  objecteContracte: string;
  pressupost: string;
  seleccionSubtipusContracte: string;
  seleccionaIDAmbit: string;
  seleccionaLot: string;
  seleccionaTipus: string;
  seleccionaTipusResultat: string;
  subtipusContracte: string;
  tipusContracte: string;
  tipusProcediment: string;
  tipusResultat: string;
  urlContracte: string;
  valorEstimat: string;


  constructor(private ambitservice: AmbitService,
              private lotservice: LotService,
              private usuariService: UsuariService,
              private contracteService: ContracteService,
              private router: Router,
              private alertController: AlertController,
              private translate: TranslateService) {
    addIcons({searchOutline});
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    this.minDate = tomorrow.toISOString().slice(0, 10);
  }

  ngOnInit() {
    this.loadFormGroup();
    this.loadUsuari();
    this.loadAmbit();
    this.loadLot();
    this.loadSTContractes();
    this.loadSTContractesPerLletres();
  }

  private loadFormGroup() {
    this.formGroup = new FormGroup({
      codiEx: new FormControl(null, [Validators.required]),
      TContracte: new FormControl(null),
      STContracte: new FormControl(null),
      TProcediment: new FormControl(null),
      obCont: new FormControl(null),
      pressu: new FormControl(null),
      valest: new FormControl(null),
      llocExc: new FormControl(null),
      anys: new FormControl(null),
      mesos: new FormControl(null, [Validators.max(12)]),
      dies: new FormControl(null, [Validators.max(31)]),
      term: new FormControl(null, [Validators.required]),
      TResultat: new FormControl(''),
      url: new FormControl(''),
      idA: new FormControl(null, [Validators.required]),
      nomLot: new FormControl(''),
      idLot: new FormControl(null),
    });
  }

  loadUsuari() {
    let idUsuaristr = localStorage.getItem('idUsuari');
    if (idUsuaristr !== null) {
      this.usuariService.getUsuari(parseInt(idUsuaristr, 10)).subscribe(data => {
        this.usuari = data;
        if (this.usuari.idioma?.codi === 'ENG') {
          this.translate.use('en');
        } else if (this.usuari.idioma?.codi === 'CAST') {
          this.translate.use('es');
        } else {
          this.translate.use('ca');
        }
        this.translate.get('HOME.FILTRES.CODI_EXPEDIENT').subscribe((res: string) => {
          this.codiExpedient = res;
        });
        this.translate.get('HOME.FILTRES.INTRODUIR_CODI').subscribe((res: string) => {
          this.introdueixCodi = res;
        });
        this.translate.get('HOME.FILTRES.TIPUS_CONTRACTE').subscribe((res: string) => {
          this.tipusContracte = res;
        });
        this.translate.get('HOME.FILTRES.SELECCIONAR_TIPUS').subscribe((res: string) => {
          this.seleccionaTipus = res;
        });
        this.translate.get('COMUNS.CANCELAR').subscribe((res: string) => {
          this.cancelar = res;
        });
        this.translate.get('COMUNS.ACCEPTAR').subscribe((res: string) => {
          this.accepta = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.NOM_SUBTIPUS_CONTRACTE').subscribe((res: string) => {
          this.nomSubtipusContracte = res;
        });
        this.translate.get('HOME.CONTRACTE.SUBTIPUS_CONTRACTE').subscribe((res: string) => {
          this.subtipusContracte = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.SELECCIONAR_SUBTIPUS_CONTRACTE').subscribe((res: string) => {
          this.seleccionSubtipusContracte = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.TIPUS_PROCEDIMENT').subscribe((res: string) => {
          this.tipusProcediment = res;
        });
        this.translate.get('HOME.FILTRES.OBJECTE_CONTRACTE').subscribe((res: string) => {
          this.objecteContracte = res;
        });
        this.translate.get('HOME.FILTRES.INTRODUIR_OBJECTE').subscribe((res: string) => {
          this.introdueixObjecteContracte = res;
        });
        this.translate.get('HOME.CONTRACTE.PRESSUPOST').subscribe((res: string) => {
          this.pressupost = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.INTRODUIR_PRESSUPOST').subscribe((res: string) => {
          this.introdueixPressupost = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.VALOR_ESTIMAT').subscribe((res: string) => {
          this.valorEstimat = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.INTRODUIR_VALOR_ESTIMAT').subscribe((res: string) => {
          this.introdueixValorEstimat = res;
        });
        this.translate.get('HOME.FILTRES.LLOC_EXECUCIO').subscribe((res: string) => {
          this.llocExecucio = res;
        });
        this.translate.get('HOME.FILTRES.INTRODUIR_LLOC').subscribe((res: string) => {
          this.introdueixLlocExecucio = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.ANYS').subscribe((res: string) => {
          this.anys = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.NUM_ANYS').subscribe((res: string) => {
          this.numAnys = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.DIES').subscribe((res: string) => {
          this.dies = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.NUM_DIES').subscribe((res: string) => {
          this.numDies = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.MESOS').subscribe((res: string) => {
          this.mesos = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.NUM_MESOS').subscribe((res: string) => {
          this.numMesos = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.TIPUS_RESULTAT').subscribe((res: string) => {
          this.tipusResultat = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.SELECCIONAR_TIPUS_RESULTAT').subscribe((res: string) => {
          this.seleccionaTipusResultat = res;
        });
        this.translate.get('HOME.FILTRES.AMBIT').subscribe((res: string) => {
          this.ambit = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.SELECCIONAR_ID_AMBIT').subscribe((res: string) => {
          this.seleccionaIDAmbit = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.NOM_LOT').subscribe((res: string) => {
          this.nomLot = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.LOTS').subscribe((res: string) => {
          this.lots = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.SELECCIONAR_LOT').subscribe((res: string) => {
          this.seleccionaLot = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.URL_CONTRACTE').subscribe((res: string) => {
          this.urlContracte = res;
        });
        this.translate.get('PUBLICAR_CONTRACTE.INTRODUEIX_URL').subscribe((res: string) => {
          this.introdueixURL = res;
        });
      })
    }
    let codiRolStr = localStorage.getItem('codiRol');
    if (codiRolStr !== null) {
      this.codiRol = codiRolStr;
    }
  }

  loadAmbit() {
    this.ambitservice.listar().subscribe(data => {
      this.ambitList = data;
    })
  }

  loadLot() {
    const nombre = {
      nom : this.formGroup.get('nomLot')?.value
    };
    this.lotservice.buscar(nombre).subscribe(data => {
      this.lotList = data;
    })
  }

  loadSTContractes(){
    const tContracteControl = this.formGroup.get('TContracte');
    if (tContracteControl) {
      const value = tContracteControl.value; // Obtener el valor actual del FormControl
      if(value === 'Obres'){
        this.STContracteList = this.STContracteListO;
      }
      else if(value === 'Subministraments'){
        this.STContracteList = this.STContracteListSubm;
      }
      else if(value === 'Serveis'){
        this.STContracteList = this.STContracteListServ;
      }
      else if(value === 'Privat d\'Administració Pública'){
        this.STContracteList = this.STContracteListPAP;
      }
      else if(value === 'Altra legislació sectorial'){
        this.STContracteList = this.STContracteListALS;
      }
      else {
        this.STContracteList = this.STContracteListT1;
      }
    }
  }

  loadSTContractesPerLletres() {
    const stContracteValue = this.formGroup.get('STContracte')?.value;
    const elementosCoincidentes = this.STContracteList.filter(elemento => {
      // Verificar si el elemento incluye el texto de STContracte
      return elemento.includes(stContracteValue);
    });
    this.STContracteList = elementosCoincidentes;
  }

  async onSubmit() {
    const confirm = await this.mostrarConfirmacioPujarContracte();

    if (confirm) {
      const contracte = new Contracte();
      contracte.codiExpedient = this.formGroup.get("codiEx")?.value;
      contracte.tipusContracte = this.formGroup.get("TContracte")?.value;
      contracte.subtipusContracte = this.formGroup.get("STContracte")?.value;
      contracte.procediment = this.formGroup.get("TProcediment")?.value;
      contracte.objecteContracte = this.formGroup.get("obCont")?.value;
      contracte.pressupostLicitacio = this.formGroup.get("pressu")?.value;
      contracte.valorEstimatContracte = this.formGroup.get("valest")?.value;
      contracte.llocExecucio = this.formGroup.get("llocExc")?.value;

      const anys = this.formGroup.get('anys')?.value || 0;
      const mesos = this.formGroup.get('mesos')?.value || 0;
      const dies = this.formGroup.get('dies')?.value || 0;
      contracte.duracioContracte = anys + " anys, " + mesos + " mesos, " + dies + " dies.";
      contracte.terminiPresentacioOfertes = this.formGroup.get("term")?.value;
      contracte.dataPublicacioAnunci = new Date();
      contracte.ofertesRebudes = 0;
      contracte.resultat = this.formGroup.get("TResultat")?.value;
      contracte.enllacPublicacio = this.formGroup.get("url")?.value;
      contracte.ambit = this.ambitList.find(amb => amb.nom === this.formGroup.get("idA")?.value)
      contracte.lot = this.lotList.find(lo => lo.descripcio === this.formGroup.get("idLot")?.value)
      contracte.usuariCreacio = this.usuari;
      this.contracteService.publicar(contracte).subscribe(() => {
        this.router.navigate(['/home']);
        this.mostrarMissatgeContracteCreat();
      })
    }
  }

  async mostrarConfirmacioPujarContracte() {
    return new Promise(async (resolve) => {
      const alert = await this.alertController.create({
        header: 'Confirmar',
        message: 'Estàs segur/a que desitges publicar aquest contracte?',
        buttons: [
          {
            text: 'Cancel·lar',
            role: 'cancel',
            cssClass: 'secondary',
            handler: () => {
              resolve(false);
            }
          }, {
            text: 'Publicar',
            handler: () => {
              resolve(true);
            }
          }
        ]
      });
      await alert.present();
    });
  }

  mostrarMissatgeContracteCreat() {
    this.alertController.create({
      header: 'Confirmació',
      message: 'El contracte s\'ha creat correctament',
      buttons: ['D\'acord']
    }).then(alert => alert.present());
  }
}
