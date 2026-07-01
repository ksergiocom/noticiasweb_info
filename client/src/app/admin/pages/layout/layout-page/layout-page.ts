import { Component } from '@angular/core';
import { Navbar } from "../navbar/navbar";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-admin-layout-page',
  imports: [RouterOutlet, Navbar],
  templateUrl: './layout-page.html',
})
export class LayoutPage {}
