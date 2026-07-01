import { Routes } from '@angular/router';

import { LayoutPage as AdminLayoutPage } from '@/admin/pages/layout/layout-page/layout-page';

import { IndexPage as PeriodicosIndexPage } from '@/admin/pages/periodicos/index-page/index-page';
import { CreatePage as PeriodicosCreatePage } from '@/admin/pages/periodicos/create-page/create-page';
import { UpdatePage as PeriodicosUpdatePage } from '@/admin/pages/periodicos/update-page/update-page';

import { IndexPage as NoticiasIndexPage } from '@/admin/pages/noticias/index-page/index-page';

export const adminRoutes: Routes = [
  ///////////////////////////////////////////////
  // Layout
  ///////////////////////////////////////////////
  {
    path: '',
    component: AdminLayoutPage,
    children: [
      {
        path: '',
        redirectTo: 'periodicos',
        pathMatch: 'full',
      },
      ///////////////////////////////////////////////
      // Periodicos
      ///////////////////////////////////////////////
      {
        path: 'periodicos',
        children: [
          {
            path: '',
            component: PeriodicosIndexPage,
          },
          {
            path: 'crear',
            component: PeriodicosCreatePage,
          },
          {
            path: ':id/actualizar',
            component: PeriodicosUpdatePage,
          },
        ],
      },
      ///////////////////////////////////////////////
      // Noticicas
      ///////////////////////////////////////////////
      {
        path: 'noticias',
        children: [
          {
            path: '',
            component: NoticiasIndexPage,
          },
        ],
      },
    ],
  },
];
