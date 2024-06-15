import {Idioma} from "./idioma";
import {Rols} from "./rols";
import {Organ} from "./organ";

export class Usuari {
  idUsuari: number;
  nom: string;
  email: string;
  contrasenya: string;
  pais: string;
  codiPostal: number;
  direccio: string;
  telefon: string;
  enllacPerfilSocial: string;
  idioma?: Idioma;
  notificacionsActives: boolean;
  identNif: string;
  descripcio: string;
  bloquejat: boolean;
  rol?: Rols;
  organ?: Organ;
  ubicacio?: {
    lat: number,
    lng: number
  };
  valoracio: number;
  numValoracions: number;
}
