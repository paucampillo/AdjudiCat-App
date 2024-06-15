import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import {GooglecalendarComponent} from "./core/googlecalendar/googlecalendar.component";



export const routes: Routes = [
  {
    path  : 'home',
    loadComponent: () => import('./core/pages/home/home.page').then((m) => m.HomePage),
  },
  {
    path: 'contracte/:idContracte',
    loadComponent: () => import('./core/pages/home/contracte/contracte.page').then(m => m.ContractePage)
  },
  {
    path: 'contracte/:idContracte/licitar',
    loadComponent: () => import('./core/pages/home/contracte/licitar/licitar.page').then(m => m.LicitarPage)

  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('./core/pages/login/login.page').then( m => m.LoginPage)
  },
  {
    path: 'signup',
    loadComponent: () => import('./core/pages/signup/signup.page').then( m => m.SignupPage)
  },
  {
    path: 'profile',
    loadComponent: () => import('./core/pages/profile/profile.page').then(m => m.ProfilePage),
  },
  {
  path: 'profile/edit',
    loadComponent: () => import('./core/pages/profile/editar-perfil/editar-perfil.page').then(m => m.EditarPerfilPage)
  },
  {
    path: 'profile/password',
    loadComponent: () => import('./core/pages/profile/canvi-contrasenya/canvi-contrasenya.page').then( m => m.CanviContrasenyaPage)
  },
  {
    path: 'publicar-contracte',
    loadComponent: () => import('./core/pages/publicar-contracte/publicar-contracte.page').then( m => m.PublicarContractePage)
  },
  {
    path: 'licitacions',
    loadComponent: () => import('./core/pages/licitacions/licitacions.page').then(m => m.LicitacionsPage)
  },
  {
    path: 'tipus-licitacions',
    loadComponent: () => import('./core/pages/licitacions/tipus-licitacions/tipus-licitacions.page').then( m => m.TipusLicitacionsPage)
  },
  {
    path: 'contactos-mensajes/:idUserEmisor',
    loadComponent: () => import('./core/pages/contactos-mensajes/contactos-mensajes.page').then( m => m.ContactosMensajesPage)
  },
  {
    path: 'perfil-extern-organ/:idUsuari',
    loadComponent: () => import('./core/pages/perfil-extern/perfil.page').then(m => m.PerfilPage)
  },
  {
    path: 'chat-mensajes/:idUserEmisor/:idUserReceptor',
    loadComponent: () => import('./core/pages/chat-mensajes/chat-mensajes.page').then( m => m.ChatMensajesPage)
  },
  {
    path: 'alertes',
    loadComponent: () => import('./core/googlecalendar/googlecalendar.component').then( m => m.GooglecalendarComponent)
  },
  {
    path: 'valoracions/:tipusVal',
    loadComponent: () => import('./core/pages/valoracions/valoracions.page').then(m => m.ValoracionsPage)
  },
  {
    path: 'valoracions/:tipusVal/:idOrgan',
    loadComponent: () => import('./core/pages/valoracions/valoracions.page').then(m => m.ValoracionsPage)
  },
  {
    path: 'profile/gestio-usuaris',
    loadComponent: () => import('./core/pages/profile/gestio-usuaris/gestio-usuaris.page').then( m => m.GestioUsuarisPage)
  },
  {
    path: 'profile/compte-google',
    loadComponent: () => import('./core/pages/profile/compte-google/compte-google.page').then( m => m.CompteGooglePage)
  },











];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules }),
    HttpClientModule
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
