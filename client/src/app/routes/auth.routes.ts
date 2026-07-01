import { LoginPage } from '@/auth/pages/login-page/login-page';
import { Routes } from '@angular/router';

export const authRoutes: Routes = [
    {
        path:'',
        redirectTo:'login',
        pathMatch:'full',
    },
    {
        path:'login',
        component: LoginPage,
    }
];
