import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import {AuthService} from "../services/auth.service";
import {UsuariService} from "../services/usuari.service";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  exceptionsURLs = [
    this.authSvc.LOGIN,
    this.authSvc.GOOGLE_LOGIN,
    this.usuariSvc.REGISTER
  ];
  constructor(private authSvc: AuthService,
              private usuariSvc: UsuariService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if (this.exceptionsURLs.find(item => item == request.url)) {
      return next.handle(request);
    }
    if (this.authSvc.existsToken) {
      return next.handle(request.clone({
        headers: request.headers.set('Authorization', this.authSvc.getToken),
      }));
    }
    return next.handle(request);
  }
}
