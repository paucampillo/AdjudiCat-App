import { Component, Input, OnInit } from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {NgStyle} from "@angular/common";

@Component({
  selector: 'app-tarjeta-contacte',
  templateUrl: './tarjeta-contacte.component.html',
  styleUrls: ['./tarjeta-contacte.component.scss'],
  imports: [
    IonicModule,
    NgStyle,
  ],
  standalone: true
})
export class TarjetaContacteComponent  implements OnInit {
  @Input() imagenLogo: string;
  @Input() titulo: string;
  @Input() fecha: string;
  @Input() numMensajes: string;
  @Input() contacteStyle: { [key: string]: string } = {};
  constructor() { }

  ngOnInit() {}

}
