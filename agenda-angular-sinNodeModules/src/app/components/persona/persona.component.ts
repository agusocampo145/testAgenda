import { Component, OnInit } from '@angular/core';
import { PersonaService } from '../../services/persona-service/persona.service';
import { CiudadService } from '../../services/ciudad-service/ciudad.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Ciudad } from '../../interfaces/ciudad';
import { Persona } from '../../interfaces/persona';
import { log } from 'console';

@Component({
  selector: 'app-persona',
  templateUrl: './persona.component.html',
  styleUrls: ['./persona.component.scss'],
  imports: [CommonModule, FormsModule],
})
export class PersonaComponent implements OnInit {
  personas: Persona[] = [];
  ciudades: Ciudad[] = [];
  nuevaPersona: Persona = {
    id: 0,
    nombre: '',
    apellido: '',
    telefono: '',
    ciudad: {
      id: 0,
      nombre: '',
      provincia: '',
      pais: '',
    },
  };

  nombreNuevaPersona: string = '';
  apellidoNuevaPersona: string = '';
  telefonoNuevaPersona: string = '';
  ciudadNuevaPersona: Ciudad = {
    id: 0,
    nombre: '',
    provincia: '',
    pais: '',
  };
  persona: Persona = {
    id: 0,
    nombre: '',
    apellido: '',
    telefono: '',
    ciudad: {
      id: 0,
      nombre: '',
      provincia: '',
      pais: '',
    },
  };

  mensajeError = '';
  mensajeExito = '';
  agregandoNuevaCiudad = false;
  mensajeCiudadError = '';
  nuevaCiudad: Ciudad = { id: 0, nombre: '', provincia: '', pais: '' };
  filtroNombre: string = '';
  personasFiltradas: Persona[] = [];
  busquedaNombre: string = '';
  busquedaCiudad: Ciudad | null = null;
  busquedaNombreMultiple: string = '';
  busquedaCiudadesMultiples: string[] = [];

  constructor(
    private personaService: PersonaService,
    private ciudadService: CiudadService
  ) {}

  ngOnInit(): void {
    this.getCiudades();
    this.listarTodasLasPersonas();
  }

  getCiudades(): void {
    this.ciudadService
      .listar()
      .subscribe((ciudades) => (this.ciudades = ciudades));
  }

  agregarPersona(): void {
    this.mensajeError = '';
    this.mensajeExito = '';
    this.mensajeCiudadError = '';

    this.nuevaPersona.nombre = this.nombreNuevaPersona;
    this.nuevaPersona.apellido = this.apellidoNuevaPersona;
    this.nuevaPersona.telefono = this.telefonoNuevaPersona;
    this.nuevaPersona.ciudad = this.ciudadNuevaPersona;

    this.personaService.crear(this.nuevaPersona).subscribe({
      next: () => {
        this.mensajeExito = '¡Persona agregada exitosamente!';
        this.listarTodasLasPersonas();
      },
      error: (err) => {
        if (err.status === 400) {
          this.mensajeError = 'Los datos de la persona son inválidos.';
          return;
        }
        if (err.status === 409) {
          this.mensajeCiudadError =
            'Ya existe una persona con ese número de teléfono.';
          return;
        }
        this.mensajeError = 'Ocurrió un error al agregar la persona.';
      },
    });
  }

  mostrarFormularioNuevaCiudad() {
    this.agregandoNuevaCiudad = true;
    this.mensajeCiudadError = '';
    this.nuevaCiudad = { id: 0, nombre: '', provincia: '', pais: '' };
  }

  cancelarNuevaCiudad() {
    this.agregandoNuevaCiudad = false;
    this.mensajeCiudadError = '';
  }

  agregarCiudad() {
    if (
      !this.nuevaCiudad.nombre ||
      !this.nuevaCiudad.provincia ||
      !this.nuevaCiudad.pais
    ) {
      this.mensajeCiudadError = 'Todos los campos son obligatorios.';
      return;
    }
    const ciudadParaCrear: any = {
      nombre: this.nuevaCiudad.nombre,
      provincia: this.nuevaCiudad.provincia,
      pais: this.nuevaCiudad.pais,
    };

    this.ciudadService.crear(ciudadParaCrear).subscribe({
      next: (ciudad) => {
        this.ciudades.push(ciudad);
        this.ciudadNuevaPersona = ciudad;
        this.agregandoNuevaCiudad = false;
      },
      error: (e) => {
        //Para otros casos, puede crearse un ENUM con los distintos mensajes de error, no tener que escribir el mensaje 'string' del error en cada situacion
        if (e.status == 400) {
          this.mensajeCiudadError = 'Los datos de la ciudad son inválidos.';
          return;
        } else if (e.status == 409) {
          this.mensajeCiudadError = 'La ciudad ya existe.';
          return;
        }
        this.mensajeCiudadError = 'No se pudo agregar la ciudad.';
      },
    });
  }

  listarTodasLasPersonas(): void {
    this.personaService.listar().subscribe((personas) => {
      this.personas = personas;
      this.personasFiltradas = personas; 
    });
  }

  filtrarPersonas(): void {
    const filtro = this.filtroNombre.trim().toLowerCase();
    if (filtro === '') {
      this.personasFiltradas = this.personas;
    } else {
      this.personasFiltradas = this.personas.filter((p) =>
        p.nombre.toLowerCase().startsWith(filtro)
      );
    }
  }

  buscarPorNombre() {
    if (!this.busquedaNombre.trim()) {
      this.personasFiltradas = this.personas;
      return;
    }
    this.personaService
      .buscarPorNombre(this.busquedaNombre.trim())
      .subscribe((personas) => {
        this.personasFiltradas = personas;
      });
  }

  buscarPorCiudad() {
    if (!this.busquedaCiudad) {
      this.personasFiltradas = this.personas;
      return;
    }
    this.personaService
      .buscarPorCiudad(this.busquedaCiudad)
      .subscribe((personas) => {
        this.personasFiltradas = personas;
      });
  }

  buscarPorNombreYCiudades() {
    if (
      !this.busquedaNombreMultiple.trim() ||
      this.busquedaCiudadesMultiples.length === 0
    ) {
      this.personasFiltradas = this.personas;
      return;
    }
    this.personaService
      .buscarPorNombreYCiudades(
        this.busquedaNombreMultiple.trim(),
        this.busquedaCiudadesMultiples
      )
      .subscribe((personas) => {
        this.personasFiltradas = personas;
      });
  }

  onCiudadMultipleChange(event: any, nombreCiudad: string) {
    if (event.target.checked) {
      if (!this.busquedaCiudadesMultiples.includes(nombreCiudad)) {
        this.busquedaCiudadesMultiples.push(nombreCiudad);
      }
    } else {
      this.busquedaCiudadesMultiples = this.busquedaCiudadesMultiples.filter(
        (c) => c !== nombreCiudad
      );
    }
  }

  limpiarFiltros(): void {
    this.personasFiltradas = this.personas;
  }
}
