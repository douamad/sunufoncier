import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CategorieBatieComponent } from '../list/categorie-batie.component';
import { CategorieBatieDetailComponent } from '../detail/categorie-batie-detail.component';
import { CategorieBatieUpdateComponent } from '../update/categorie-batie-update.component';
import { CategorieBatieRoutingResolveService } from './categorie-batie-routing-resolve.service';

const categorieBatieRoute: Routes = [
  {
    path: '',
    component: CategorieBatieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategorieBatieDetailComponent,
    resolve: {
      categorieBatie: CategorieBatieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategorieBatieUpdateComponent,
    resolve: {
      categorieBatie: CategorieBatieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategorieBatieUpdateComponent,
    resolve: {
      categorieBatie: CategorieBatieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(categorieBatieRoute)],
  exports: [RouterModule],
})
export class CategorieBatieRoutingModule {}
