import { Component, OnInit } from '@angular/core';
import { CiudadService } from '../../services/ciudad-service/ciudad.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-ciudad',
  templateUrl: './ciudad.component.html',
  styleUrls: ['./ciudad.component.scss'],
  imports: [CommonModule, FormsModule]
})
export class CiudadComponent implements OnInit {
  ciudades: any[] = [];
  nuevaCiudad: any = {
    nombre: '',
    provincia: '',
    pais: ''
  };

  mensajeError = '';
  mensajeExito = '';

  constructor(private ciudadService: CiudadService) {}

  ngOnInit(): void {
    this.listarCiudades();
  }

  listarCiudades() {
    this.ciudadService.listar().subscribe(ciudades => {
      this.ciudades = ciudades;
    });
  }

  agregarCiudad() {
    this.mensajeError = '';
    this.mensajeExito = '';
    if (
      !this.nuevaCiudad.nombre ||
      !this.nuevaCiudad.provincia ||
      !this.nuevaCiudad.pais
    ) {
      this.mensajeError = 'Todos los campos son obligatorios.';
      return;
    }
    this.ciudadService.crear(this.nuevaCiudad).subscribe({
      next: (ciudad) => {
        this.mensajeExito = '¡Ciudad agregada exitosamente!';
        this.nuevaCiudad = { nombre: '', provincia: '', pais: '' };
        this.listarCiudades();
      },
      error: (e) => {
        if (e.status == 400) {
          this.mensajeError = 'Los datos de la ciudad son inválidos.';
          return;
        }
        if (e.status == 409) {
          this.mensajeError = 'La ciudad ya existe.';
          return;
        }
        this.mensajeError = 'No se pudo agregar la ciudad.';
      }
    });
  }
}