import { Component, OnInit, Input } from '@angular/core';
import {IonicModule, NavController} from "@ionic/angular";
import {NgIf, NgStyle} from "@angular/common";
import {addIcons} from "ionicons";
import {arrowBack} from "ionicons/icons";

@Component({
  selector: 'app-header-generico',
  templateUrl: './header-generico.component.html',
  styleUrls: ['./header-generico.component.scss'],
  imports: [
    IonicModule,
    NgStyle,
    NgIf
  ],
  standalone: true
})
export class HeaderGenericoComponent implements OnInit {
  @Input() backgroundName: string;
  @Input() backButton: boolean;
  @Input() adjudibot: boolean;
  @Input() titulo: string;
  constructor(private navCtrl: NavController) {
    addIcons({"arrow-back": arrowBack});
  }

  ngOnInit() {}

  navigateAdjudibot() {
    const userID = localStorage.getItem('idUsuari');
    this.navCtrl.navigateRoot('/chat-mensajes/' + userID + '/19')
  }
}
