import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import {MenuComponent} from "../menu/menu.component";
import {GooglecalendarComponent} from "../../googlecalendar/googlecalendar.component";
import {HeaderGenericoComponent} from "../ComponentesCabecera/header-generico/header-generico.component";

@Component({
  selector: 'app-alertes',
  templateUrl: './alertes.page.html',
  styleUrls: ['./alertes.page.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, HeaderGenericoComponent, MenuComponent, GooglecalendarComponent]
})
export class AlertesPage implements OnInit {
  selectedDate: string;

  constructor() { }

  ngOnInit() {
  }

}
