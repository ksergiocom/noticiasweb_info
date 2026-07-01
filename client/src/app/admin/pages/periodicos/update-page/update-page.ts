import { PeriodicoForm } from '@/noticias/interfaces/Periodico';
import { PeriodicoService } from '@/noticias/services/periodico-service';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-periodico-update-page',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './update-page.html',
})
export class UpdatePage {
  ///////////////////////////////////////////////
  // Servicios
  ///////////////////////////////////////////////
  public fb = inject(FormBuilder);
  public router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);
  public periodicoService = inject(PeriodicoService);


  ///////////////////////////////////////////////
  // Data
  ///////////////////////////////////////////////
  public form: FormGroup<PeriodicoForm> = this.fb.nonNullable.group({
    nombre: [''],
    url: [''],
    logo: [''],
    rss: ['']
  });


  ///////////////////////////////////////////////
  // Metodos
  ///////////////////////////////////////////////
  public ngOnInit(){
    console.log(this.activatedRoute);
    const periodicoId = this.activatedRoute.snapshot.paramMap.get("id");
    if(!periodicoId)
      throw Error("No existe este periodico");

    this.periodicoService.obtener(Number(periodicoId)).subscribe({
      next: (periodico) => {
        const data = {
          nombre: periodico.nombre,
          url: periodico.url,
          logo: periodico.logo,
          rss: periodico.rss,
        }
        this.form.setValue(data)
      }
    })
  }

  public onSubmit() {
    const periodicoId = this.activatedRoute.snapshot.paramMap.get("id");
    this.periodicoService.actualizar(Number(periodicoId), this.form.value).subscribe({
      next: (res) => this.router.navigate(['/admin/periodicos']),
      error: (res) => alert("Woups! Algo salió mal al intentar actualizar el periódico")
    });
  }
  
}
