import { Routes } from '@angular/router';

import {HomePage} from '@/public/pages/home-page/home-page'
import {AboutPage} from '@/public/pages/about-page/about-page'

export const publicRoutes: Routes = [
    {
        path:'',
        component: HomePage,
    },
    {
        path:'sobre',
        component: AboutPage,
    },
];
