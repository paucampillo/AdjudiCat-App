import {Lot} from "./lot";
import {Ambit} from "./ambit";
import {Usuari} from "./usuari";

export class Contracte {
  idContracte: number;
  codiExpedient: string;
  tipusContracte: string;
  subtipusContracte: string;
  procediment: string;
  objecteContracte: string;
  pressupostLicitacio: number;
  valorEstimatContracte: number;
  llocExecucio: string;
  duracioContracte: string;
  terminiPresentacioOfertes: Date;
  dataPublicacioAnunci: Date;
  ofertesRebudes: number;
  resultat: string;
  enllacPublicacio: string;
  dataAdjudicacioContracte: Date;
  ambit?: Ambit;
  lot?: Lot;
  usuariCreacio: Usuari;
  preferit: boolean;
}
