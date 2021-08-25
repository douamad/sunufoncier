import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorieClotureComponent } from '../list/categorie-cloture.component';
import { CategorieClotureDetailComponent } from '../detail/categorie-cloture-detail.component';
import { CategorieClotureUpdateComponent } from '../update/categorie-cloture-update.component';
import { CategorieClotureRoutingResolveService } from './categorie-cloture-routing-resolve.service';

const categorieClotureRoute: Routes = [
  {
    path: '',
    component: CategorieClotureComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieClotureDetailComponent,
    resolve: {
      categorieCloture: CategorieClotureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieClotureUpdateComponent,
    resolve: {
      categorieCloture: CategorieClotureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieClotureUpdateComponent,
    resolve: {
      categorieCloture: CategorieClotureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categorieClotureRoute)],
  exports: [RouterModule],
})
export class CategorieClotureRoutingModule {}
