import { Ciudad } from './ciudad';

export interface Persona {
  id: number;
  nombre: string;
  apellido: string;
  telefono: string;
  ciudad: Ciudad;
}
