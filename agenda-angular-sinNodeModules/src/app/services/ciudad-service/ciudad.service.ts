import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Ciudad } from '../../interfaces/ciudad';

@Injectable({ providedIn: 'root' })
export class CiudadService {
  private apiUrl = `${environment.apiUrl}/ciudades`;

  constructor(private http: HttpClient) {}

  listar(): Observable<Ciudad[]> {
    return this.http.get<Ciudad[]>(this.apiUrl);
  }

  crear(ciudad: any): Observable<Ciudad> {
    return this.http.post<Ciudad>(this.apiUrl, ciudad);
  }
}
