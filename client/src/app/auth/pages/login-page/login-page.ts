import { AuthService } from '@/auth/services/auth-service';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  imports: [ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css',
})
export class LoginPage {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  public form = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  public onSubmit() {
    console.log(this.form.value);
    this.authService.login({
      username:this.form.value.username ?? '',
      password:this.form.value.password ?? '',
    }).subscribe({
      next: (res) => {
        this.router.navigate(["/admin"])
      },
      error: () => {
        this.form.reset()
      }
    })
  }
}
