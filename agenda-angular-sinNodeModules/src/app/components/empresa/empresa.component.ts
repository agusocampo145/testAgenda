import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../../services/empresa-service/empresa.service';
import { CiudadService } from '../../services/ciudad-service/ciudad.service';
import { PersonaService } from '../../services/persona-service/persona.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Empresa } from '../../interfaces/empresa';

@Component({
  selector: 'app-empresa',
  templateUrl: './empresa.component.html',
  styleUrls: ['./empresa.component.scss'],
  imports: [FormsModule, CommonModule],
})
export class EmpresaComponent implements OnInit {
  empresas: any[] = [];
  ciudades: any[] = [];
  personas: any[] = [];
  nuevaEmpresa: Empresa = {
    razonSocial: '',
    cuit: '',
    ciudad: {
      id: 0,
      nombre: '',
      provincia: '',
      pais: '',
    },
  };
  mensajeError = '';
  mensajeExito = '';
  nuevoContacto: { [key: number]: any } = {};
  mensajeErrorContacto: { [key: number]: string } = {};
  mensajeExitoContacto: { [key: number]: string } = {};
  empresaSeleccionada: any = null;

  constructor(
    private empresaService: EmpresaService,
    private ciudadService: CiudadService,
    private personaService: PersonaService
  ) {}

  ngOnInit(): void {
    this.listarEmpresas();
    this.listarCiudades();
    this.listarPersonas();
  }

  listarEmpresas() {
    this.empresaService.listar().subscribe((empresas) => {
      this.empresas = empresas;
    });
  }

  listarCiudades() {
    this.ciudadService.listar().subscribe((ciudades) => {
      this.ciudades = ciudades;
    });
  }

  listarPersonas() {
    this.personaService.listar().subscribe((personas) => {
      this.personas = personas;
    });
  }

  agregarEmpresa() {
    this.mensajeError = '';
    this.mensajeExito = '';
    if (
      !this.nuevaEmpresa.razonSocial ||
      !this.nuevaEmpresa.cuit ||
      !this.nuevaEmpresa.ciudad
    ) {
      this.mensajeError = 'Todos los campos son obligatorios.';
      return;
    }
    this.empresaService.crear(this.nuevaEmpresa).subscribe({
      next: () => {
        this.mensajeExito = '¡Empresa agregada exitosamente!';
        this.nuevaEmpresa = {
          razonSocial: '',
          cuit: '',
          ciudad: {
            id: 0,
            nombre: '',
            provincia: '',
            pais: '',
          },
        };
        this.listarEmpresas();
      },
      error: (e) => {
        if (e.status == 400) {
          this.mensajeError = 'Los datos de la empresa son inválidos.';
          return;
        }
        if (e.status == 409) {
          this.mensajeError = 'Ya existe una empresa con ese nombre.';
          return;
        }
        this.mensajeError = 'No se pudo agregar la empresa.';
      },
    });
  }

  mostrarEmpresaSeleccionada() {
    if (this.empresaSeleccionada) {
      const id = this.empresaSeleccionada.id;
      this.mensajeErrorContacto[id] = '';
      this.mensajeExitoContacto[id] = '';
    }
  }

  agregarContactoAEmpresa(empresaId: number, persona: any) {
    this.mensajeErrorContacto[empresaId] = '';
    this.mensajeExitoContacto[empresaId] = '';
    if (!persona) {
      this.mensajeErrorContacto[empresaId] = 'Debe seleccionar un contacto.';
      return;
    }
    this.empresaService.agregarContacto(empresaId, persona.id).subscribe({
      next: () => {
        this.mensajeExitoContacto[empresaId] = '¡Contacto agregado!';
        this.listarEmpresas();
        this.limpiarSeleccion();
      },
      error: (e) => {
        if (e.status == 400 || e.status == 404) {
          this.mensajeErrorContacto[empresaId] =
            'El contacto debe existir previamente en la agenda.';
          return;
        }
        this.mensajeErrorContacto[empresaId] =
          'No se pudo agregar el contacto.';
      },
    });
  }

  limpiarSeleccion(): void {
    this.empresaSeleccionada = null;
  }

  formatearCuit(event: any) {
    let valor = event.target.value.replace(/\D/g, ''); 
    if (valor.length > 2) valor = valor.slice(0, 2) + '-' + valor.slice(2);
    if (valor.length > 11)
      valor = valor.slice(0, 11) + '-' + valor.slice(11, 12);
    if (valor.length > 13) valor = valor.slice(0, 13); 
    this.nuevaEmpresa.cuit = valor;
  }
}
