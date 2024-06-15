import {Contracte} from "./contracte";
import {Usuari} from "./usuari";

export class Oferta {
  idOferta: number;
  empresa: Usuari;
  contracte: Contracte;
  importAdjudicacioSenseIva: number;
  importAdjudicacioAmbIva: number;
  dataHoraOferta: Date;
  ganadora: boolean;
}
