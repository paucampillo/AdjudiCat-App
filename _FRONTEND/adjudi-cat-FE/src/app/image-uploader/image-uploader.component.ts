import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-image-uploader',
  templateUrl: './image-uploader.component.html',
  styleUrls: ['./image-uploader.component.scss'],
  standalone: true
})
export class ImageUploaderComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

  selectedFile: File | null = null;

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  uploadImage() {
    if (this.selectedFile) {
      // Aquí puedes implementar la lógica para subir la imagen al servidor
      // Puedes usar servicios como HttpClient para subir la imagen al servidor
    } else {
    }
  }
}

