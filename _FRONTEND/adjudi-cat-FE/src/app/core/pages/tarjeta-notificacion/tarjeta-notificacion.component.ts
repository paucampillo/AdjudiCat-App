import {Component, Input, OnInit} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {NgStyle} from "@angular/common";

@Component({
  selector: 'app-tarjeta-notificacion',
  templateUrl: './tarjeta-notificacion.component.html',
  styleUrls: ['./tarjeta-notificacion.component.scss'],
})
export class TarjetaNotificacionComponent  implements OnInit {
  @Input() imagenLogo: string;
  @Input() titulo: string;
  @Input() fecha: string;
  @Input() numMensajes: string;
  @Input() contacteStyle: { [key: string]: string } = {};
  @Input() estatAlerta: string;
  constructor() { }

  ngOnInit() {}

}
