import { Periodico } from "./Periodico";

export interface Noticia {
    id: number;
    titulo: string,
    url: string;
    descripcion: string;
    fechaPublicacion: Date;
    fechaCreacion: Date;
    fechaActualizacion: Date;
    periodico: Periodico;
}