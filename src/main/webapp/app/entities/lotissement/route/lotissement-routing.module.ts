import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LotissementComponent } from '../list/lotissement.component';
import { LotissementDetailComponent } from '../detail/lotissement-detail.component';
import { LotissementUpdateComponent } from '../update/lotissement-update.component';
import { LotissementRoutingResolveService } from './lotissement-routing-resolve.service';

const lotissementRoute: Routes = [
  {
    path: '',
    component: LotissementComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LotissementDetailComponent,
    resolve: {
      lotissement: LotissementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LotissementUpdateComponent,
    resolve: {
      lotissement: LotissementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LotissementUpdateComponent,
    resolve: {
      lotissement: LotissementRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(lotissementRoute)],
  exports: [RouterModule],
})
export class LotissementRoutingModule {}
