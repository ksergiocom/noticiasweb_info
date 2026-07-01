import { Routes } from '@angular/router';

import { adminRoutes } from './admin.routes';
import { publicRoutes } from './public.routes';
import { authRoutes } from './auth.routes';

import { adminGuard } from '@/auth/guards/admin.guard';

export const routes: Routes = [
    {
        path:'',
        children: publicRoutes,
    },
    {
        path:'admin',
        children: adminRoutes,
        canActivate: [adminGuard]
    },
    {
        path:'auth',
        children: authRoutes,
    },
];
