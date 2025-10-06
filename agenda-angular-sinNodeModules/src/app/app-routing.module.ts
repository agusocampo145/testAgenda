// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CiudadComponent } from './components/ciudad/ciudad.component';
import { EmpresaComponent } from './components/empresa/empresa.component';
import { PersonaComponent } from './components/persona/persona.component';


const routes: Routes = [
  { path: 'personas', component: PersonaComponent },
  { path: 'empresas', component: EmpresaComponent },
  { path: 'ciudades', component: CiudadComponent },
  { path: '', redirectTo: '/empresas', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
