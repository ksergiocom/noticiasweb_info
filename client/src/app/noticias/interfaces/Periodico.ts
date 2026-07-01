import { FormControl } from "@angular/forms";

import { Noticia } from "./Noticia";

export interface Periodico {
    id: number;
    nombre: string;
    url: string;
    logo: string;
    rss: string;
    fechaCreacion: Date;
    fechaActualizacion: Date;
    noticias?: Noticia[];
}

export interface CrearPeriodicoDTO {
    nombre: string;
    url: string;
    logo: string;
    rss: string;
}

export interface ActualizarPeriodicoDTO {
    nombre?: string;
    url?: string;
    logo?: string;
    rss?: string;
}

export interface PeriodicoForm {
  nombre: FormControl<string>;
  url:    FormControl<string>;
  logo:   FormControl<string>;
  rss:    FormControl<string>;
}