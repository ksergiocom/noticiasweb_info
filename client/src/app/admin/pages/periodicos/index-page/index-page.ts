import { Periodico } from '@/noticias/interfaces/Periodico';
import { PeriodicoService } from '@/noticias/services/periodico-service';
import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-index-page',
  imports: [RouterLink],
  templateUrl: './index-page.html',
  styleUrl: './index-page.css',
})
export class IndexPage {
  public periodicosService = inject(PeriodicoService);
  public periodicos = signal<Periodico[]>([]);

  public ngOnInit() {
    this.cargarPeriodicos();
  }

  public cargarPeriodicos() {
    this.periodicosService.listar().subscribe({
      next: (periodicos) => {
        this.periodicos.set(periodicos);
      },
    });
  }

  public handleDelete(id: number){
    if(!confirm("¿Seguro que quieres eliminarlo?"))
      return;
    this.periodicosService.eliminar(id).subscribe({
      next: () => {
        this.cargarPeriodicos();
      }
    })
  }
}
