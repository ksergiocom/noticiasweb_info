import { Noticia } from '@/noticias/interfaces/Noticia';
import { NoticiasService } from '@/noticias/services/noticias-service';
import { Component, inject, signal } from '@angular/core';

@Component({
  selector: 'app-index-page',
  imports: [],
  templateUrl: './index-page.html',
  styleUrl: './index-page.css',
})
export class IndexPage {
  public noticiasService = inject(NoticiasService);
  public noticias = signal<Noticia[]>([]);

  public ngOnInit(){
    this.noticiasService.listar().subscribe({
      next:(res) => {
        this.noticias.set(res.data);
      }
    })
  }
}
