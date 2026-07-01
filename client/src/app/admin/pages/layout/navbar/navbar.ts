import { AuthService } from '@/auth/services/auth-service';
import { Component, inject } from '@angular/core';
import { Router, RouterLink } from "@angular/router";

@Component({
  selector: 'app-admin-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
})
export class Navbar {
  public authService = inject(AuthService);
  public router = inject(Router);

  public logout(){
    this.authService.logout();
    this.router.navigate(["/"]);
  }
}
