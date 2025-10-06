import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Persona } from '../../interfaces/persona';
import { Ciudad } from '../../interfaces/ciudad';

@Injectable({ providedIn: 'root' })
export class PersonaService {
  private apiUrl = `${environment.apiUrl}/personas`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Persona[]> {
    return this.http.get<Persona[]>(this.apiUrl);
  }

  crear(persona: Persona): Observable<Persona> {
    return this.http.post<Persona>(this.apiUrl, persona);
  }

  buscarPorNombre(nombre: string): Observable<Persona[]> {
    return this.http.get<Persona[]>(
      `${this.apiUrl}/buscar/nombre?nombre=${nombre}`
    );
  }

  buscarPorCiudad(ciudad: Ciudad): Observable<Persona[]> {
    return this.http.post<Persona[]>(`${this.apiUrl}/buscar/ciudad`, ciudad);
  }

  buscarPorNombreYCiudades(
    nombre: string,
    ciudades: string[]
  ): Observable<Persona[]> {
    const params = new URLSearchParams();
    params.append('nombre', nombre);
    ciudades.forEach((c) => params.append('ciudades', c));
    return this.http.get<Persona[]>(
      `${this.apiUrl}/buscar/nombre-ciudades?${params.toString()}`
    );
  }
}
