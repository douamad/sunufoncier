import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RefcadastraleComponent } from '../list/refcadastrale.component';
import { RefcadastraleDetailComponent } from '../detail/refcadastrale-detail.component';
import { RefcadastraleUpdateComponent } from '../update/refcadastrale-update.component';
import { RefcadastraleRoutingResolveService } from './refcadastrale-routing-resolve.service';

const refcadastraleRoute: Routes = [
  {
    path: '',
    component: RefcadastraleComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RefcadastraleDetailComponent,
    resolve: {
      refcadastrale: RefcadastraleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RefcadastraleUpdateComponent,
    resolve: {
      refcadastrale: RefcadastraleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RefcadastraleUpdateComponent,
    resolve: {
      refcadastrale: RefcadastraleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(refcadastraleRoute)],
  exports: [RouterModule],
})
export class RefcadastraleRoutingModule {}
