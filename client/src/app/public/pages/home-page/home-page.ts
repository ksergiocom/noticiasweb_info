import { Noticia } from '@/noticias/interfaces/Noticia';
import { Periodico } from '@/noticias/interfaces/Periodico';

import { NoticiasService } from '@/noticias/services/noticias-service';
import { PeriodicoService } from '@/noticias/services/periodico-service';
import { SelectorPeriodicos } from '@/shared/components/selector-periodicos/selector-periodicos';

import { Component, computed, DestroyRef, ElementRef, HostListener, inject, signal, ViewChild } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Subject, debounceTime, distinctUntilChanged } from 'rxjs';

@Component({
  selector: 'app-home-page',
  imports: [SelectorPeriodicos],
  templateUrl: './home-page.html',
})
export class HomePage {
  ///////////////////////////////////////////////
  // Services
  ///////////////////////////////////////////////
  private periodicosService = inject(PeriodicoService);
  private noticiasService = inject(NoticiasService);

  ///////////////////////////////////////////////
  // Data
  ///////////////////////////////////////////////

  @ViewChild('loadMoreTrigger')
  private loadMoreTrigger!: ElementRef<HTMLElement>;
  private observer?: IntersectionObserver;

  public periodicoIdsSeleccionados = signal<number[]>([]);
  public textoParaBuscar = signal<string>('');

  public periodicos = signal<Periodico[]>([]);
  public noticias = signal<Noticia[]>([]);

  public page = signal(0);
  public size = signal(25);
  public totalPages = signal(0);
  public loading = signal(false);
  public haCargado = signal(false);
  public columnCount = signal(this.calcularColumnas());
  public columnas = computed(() => {
    const n = this.columnCount();
    const cols: Noticia[][] = Array.from({ length: n }, () => []);
    this.noticias().forEach((noticia, i) => cols[i % n].push(noticia));
    return cols;
  });

  private textoBuscarSubject = new Subject<string>();
  private destroyRef = inject(DestroyRef);

  ///////////////////////////////////////////////
  // Life
  ///////////////////////////////////////////////

  public ngOnInit() {
    this.periodicosService.listar().subscribe({
      next: (data) => this.periodicos.set(data),
    });

    this.textoBuscarSubject
      .pipe(debounceTime(300), distinctUntilChanged(), takeUntilDestroyed(this.destroyRef))
      .subscribe(() => {
        this.resetearNoticias();
        this.cargarMasNoticias();
      });

    this.cargarMasNoticias();
  }

  public ngAfterViewInit() {
    this.observer = new IntersectionObserver(
      (entries) => {
        const entry = entries[0];

        if (entry.isIntersecting) {
          this.cargarMasNoticias();
        }
      },
      {
        root: null,
        rootMargin: '200px',
        threshold: 0,
      },
    );

    this.observer.observe(this.loadMoreTrigger.nativeElement);
  }

  ///////////////////////////////////////////////
  // Métodos
  ///////////////////////////////////////////////

  public cargarMasNoticias() {
    if (this.loading()) return;

    if (this.totalPages() > 0 && this.page() >= this.totalPages()) {
      return;
    }

    this.loading.set(true);

    this.noticiasService.listarPorPeriodicos(this.page(), this.size(), this.periodicoIdsSeleccionados(), this.textoParaBuscar()).subscribe({
      next: (res) => {
        this.haCargado.set(true);
        this.noticias.update((actuales) => [...actuales, ...res.data]);

        this.totalPages.set(res.totalPages);
        this.page.update((p) => p + 1);
      },
      error: () => {
        this.loading.set(false);
      },
      complete: () => {
        this.loading.set(false);
      },
    });
  }

  public handlePeriodicoToggle(p: Periodico) {
    this.periodicoIdsSeleccionados.update((ids) => {
      const yaExiste = ids.includes(p.id);

      if (yaExiste) {
        return ids.filter((id) => id !== p.id);
      }

      return [...ids, p.id];
    });

    this.resetearNoticias();
    this.cargarMasNoticias();
  }

  private resetearNoticias() {
    this.noticias.set([]);
    this.page.set(0);
    this.totalPages.set(0);
  }

  public updateTextoParaBuscar(evento: Event){
    const t = evento.target as HTMLInputElement;
    const v = t.value;
    this.textoParaBuscar.set(v);
    this.textoBuscarSubject.next(v);
  }

  ///////////////////////////////////////////////
  // Computados
  ///////////////////////////////////////////////

  public hayMasNoticias() {
    return this.totalPages() === 0 || this.page() < this.totalPages();
  }

  public recortar(texto: string, limite = 450): string {
    if (texto.length <= limite) return texto;
    return texto.slice(0, limite) + '...';
  }

  @HostListener('window:resize')
  onResize() {
    this.columnCount.set(this.calcularColumnas());
  }

  private calcularColumnas(): number {
    if (typeof window === 'undefined') return 1;
    const w = window.innerWidth;
    if (w >= 1536) return 4;
    if (w >= 1024) return 3;
    if (w >= 640) return 2;
    return 1;
  }
}
