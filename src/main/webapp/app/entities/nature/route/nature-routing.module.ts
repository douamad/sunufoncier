import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NatureComponent } from '../list/nature.component';
import { NatureDetailComponent } from '../detail/nature-detail.component';
import { NatureUpdateComponent } from '../update/nature-update.component';
import { NatureRoutingResolveService } from './nature-routing-resolve.service';

const natureRoute: Routes = [
  {
    path: '',
    component: NatureComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NatureDetailComponent,
    resolve: {
      nature: NatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NatureUpdateComponent,
    resolve: {
      nature: NatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NatureUpdateComponent,
    resolve: {
      nature: NatureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(natureRoute)],
  exports: [RouterModule],
})
export class NatureRoutingModule {}
