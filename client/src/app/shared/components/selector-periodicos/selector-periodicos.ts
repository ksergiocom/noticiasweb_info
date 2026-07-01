import { Periodico } from '@/noticias/interfaces/Periodico';
import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-selector-periodicos',
  imports: [],
  templateUrl: './selector-periodicos.html',
})
export class SelectorPeriodicos {
  public periodicos = input<Periodico[]>([]);
  public periodicoToggle = output<Periodico>();

  public seleccionados = input<number[]>([]);

  public handleClick(p: Periodico) {
    this.periodicoToggle.emit(p);
  }
}
