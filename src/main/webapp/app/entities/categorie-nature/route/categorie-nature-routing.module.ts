import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorieNatureComponent } from '../list/categorie-nature.component';
import { CategorieNatureDetailComponent } from '../detail/categorie-nature-detail.component';
import { CategorieNatureUpdateComponent } from '../update/categorie-nature-update.component';
import { CategorieNatureRoutingResolveService } from './categorie-nature-routing-resolve.service';

const categorieNatureRoute: Routes = [
  {
    path: '',
    component: CategorieNatureComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieNatureDetailComponent,
    resolve: {
      categorieNature: CategorieNatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieNatureUpdateComponent,
    resolve: {
      categorieNature: CategorieNatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieNatureUpdateComponent,
    resolve: {
      categorieNature: CategorieNatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categorieNatureRoute)],
  exports: [RouterModule],
})
export class CategorieNatureRoutingModule {}
