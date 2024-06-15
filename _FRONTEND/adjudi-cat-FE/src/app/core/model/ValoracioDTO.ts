import {Usuari} from "./usuari";

export class ValoracioDTO {
  idValoracio: number;
  autor: Usuari;
  receptor: Usuari;
  puntuacio: number;
  descripcio: string;
  dataHoraValoracio: string;
}
