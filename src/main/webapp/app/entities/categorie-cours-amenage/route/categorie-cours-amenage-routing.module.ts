import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorieCoursAmenageComponent } from '../list/categorie-cours-amenage.component';
import { CategorieCoursAmenageDetailComponent } from '../detail/categorie-cours-amenage-detail.component';
import { CategorieCoursAmenageUpdateComponent } from '../update/categorie-cours-amenage-update.component';
import { CategorieCoursAmenageRoutingResolveService } from './categorie-cours-amenage-routing-resolve.service';

const categorieCoursAmenageRoute: Routes = [
  {
    path: '',
    component: CategorieCoursAmenageComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieCoursAmenageDetailComponent,
    resolve: {
      categorieCoursAmenage: CategorieCoursAmenageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieCoursAmenageUpdateComponent,
    resolve: {
      categorieCoursAmenage: CategorieCoursAmenageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieCoursAmenageUpdateComponent,
    resolve: {
      categorieCoursAmenage: CategorieCoursAmenageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categorieCoursAmenageRoute)],
  exports: [RouterModule],
})
export class CategorieCoursAmenageRoutingModule {}
