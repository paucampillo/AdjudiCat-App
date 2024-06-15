import {Component, EventEmitter, Input, OnInit, Output, SimpleChange, SimpleChanges} from '@angular/core';
import {addIcons} from "ionicons";
import {
  arrowDownOutline,
  arrowUpOutline,
  chevronDownOutline,
  chevronForwardOutline,
  closeCircleOutline,
  pin
} from "ionicons/icons";
import {IonicModule, ModalController} from "@ionic/angular";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {TranslateModule, TranslateService} from "@ngx-translate/core";
import {UsuariService} from "../../../services/usuari.service";
import {Usuari} from "../../../model/usuari";

@Component({
  selector: 'app-filtros',
  templateUrl: './filtros.component.html',
  styleUrls: ['./filtros.component.scss'],
  imports: [
    IonicModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule
  ],
  standalone: true
})
export class FiltrosComponent implements OnInit {
  formGroup: FormGroup;
  @Output() onAccept: EventEmitter<any> = new EventEmitter;
  @Input() searchTerm: string;
  @Output() searchTermChange = new EventEmitter<string>();

  ascendent: boolean = true;

  idUsuari: number;
  usuari: Usuari;

  ambit: string;
  cancelar: string;
  codiExpedient: string;
  introduirCodi: string;
  introduirLloc: string;
  introduirObjecte: string;
  introduirValor: string;
  seleccionarTipus: string;


  constructor(private modalController: ModalController, private translate: TranslateService, private usuariServei: UsuariService) {
    addIcons({pin});
    addIcons({chevronForwardOutline});
    addIcons(({chevronDownOutline}))
    addIcons({closeCircleOutline});
    addIcons({arrowUpOutline});
    addIcons({arrowDownOutline});

    this.loadFormGroup();
  }

  ngOnInit(): void {
    this.loadUsuari();
  }

  ngOnChanges(changes: SimpleChanges) {
    if(changes['searchTerm']) {
      this.formGroup.get('nouObjecte')?.setValue(this.searchTerm || '');
    }
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
        this.translate.get('COMUNS.CANCELAR').subscribe((res: string) => {
          this.cancelar = res;
        });
        this.translate.get('HOME.FILTRES.INTRODUIR_CODI').subscribe((res: string) => {
          this.introduirCodi = res;
        });
        this.translate.get('HOME.FILTRES.SELECCIONAR_TIPUS').subscribe((res: string) => {
          this.seleccionarTipus = res;
        });
        this.translate.get('HOME.FILTRES.INTRODUIR_LLOC').subscribe((res: string) => {
          this.introduirLloc = res;
        });
        this.translate.get('HOME.FILTRES.INTRODUIR_VALOR').subscribe((res: string) => {
          this.introduirValor = res;
        });
        this.translate.get('HOME.FILTRES.INTRODUIR_OBJECTE').subscribe((res: string) => {
          this.introduirObjecte = res;
        })
      });
    }
  }

  loadFormGroup() {
    this.formGroup = new FormGroup({
      nouCodi: new FormControl(''),
      nouValor: new FormControl(''),
      nouLloc: new FormControl(''),
      tipusContracteSeleccionat: new FormControl(''),
      tipusAmbitSeleccionat: new FormControl(''),
      tipusProcedimetSeleccionat: new FormControl(''),
      nouObjecte: new FormControl(this.searchTerm || ''),
      sort: new FormControl('', [Validators.required])
    });

    this.formGroup.get('nouObjecte')?.valueChanges.subscribe(value => {
      if (value !== null) {
        this.searchTerm = value;
        this.searchTermChange.emit(this.searchTerm);
      }
    });
  }

  cancel() {
    this.modalController.dismiss({
      dismissed: true
    });
  }

  confirm() {
    const camp = this.formGroup.get('sort')?.value;
    if (this.ascendent) {
      this.formGroup.get('sort')?.setValue(camp);
    }
    else {
      this.formGroup.get('sort')?.setValue('-' + camp);
    }
    this.onAccept.emit(this.formGroup.value);
    this.modalController.dismiss()

    if (!this.ascendent) {
      let campo = this.formGroup.get('sort')?.value;
      if (campo && campo.startsWith('-')) {
        campo = campo.substring(1);
        this.formGroup.get('sort')?.setValue(campo);
      }
    }
  }

  esborraCamp(camp: string) {
    this.formGroup.get(camp)?.setValue('');
  }

  esborraTot() {
    this.formGroup.get('sort')?.setValue('');
    this.formGroup.get('tipusContracteSeleccionat')?.setValue('');
    this.formGroup.get('tipusAmbitSeleccionat')?.setValue('');
    this.formGroup.get('tipusProcedimetSeleccionat')?.setValue('');
    this.formGroup.get('nouCodi')?.setValue('');
    this.formGroup.get('nouLloc')?.setValue('');
    this.formGroup.get('nouValor')?.setValue('');
    this.formGroup.get('nouObjecte')?.setValue('');
  }

  canviarOrdre() {
    this.ascendent = !this.ascendent;
  }
}
