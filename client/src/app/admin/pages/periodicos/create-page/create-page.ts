import { PeriodicoForm } from '@/noticias/interfaces/Periodico';
import { PeriodicoService } from '@/noticias/services/periodico-service';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-periodico-create-page',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './create-page.html',
})
export class CreatePage {
  ///////////////////////////////////////////////
  // Servicios
  ///////////////////////////////////////////////
  public fb = inject(FormBuilder);
  public router = inject(Router);
  public periodicoService = inject(PeriodicoService);


  ///////////////////////////////////////////////
  // Data
  ///////////////////////////////////////////////
  public form: FormGroup<PeriodicoForm> = this.fb.nonNullable.group({
    nombre: ['', [Validators.required]],
    url: ['', [Validators.required]],
    logo: ['', [Validators.required]],
    rss: ['', [Validators.required]]
  });


  ///////////////////////////////////////////////
  // Metodos
  ///////////////////////////////////////////////
  public onSubmit() {
    this.periodicoService.crear(this.form.getRawValue()).subscribe({
      next: (res) => this.router.navigate(['/admin/periodicos']),
      error: (res) => alert("Woups! Algo salió mal al intentar crear el periódico")
    });
  }
  
}
