import { Ciudad } from './ciudad';

export interface Empresa {
  razonSocial: string;
  cuit: string;
  ciudad: Ciudad;
}
