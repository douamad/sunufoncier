import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { UsageDossierComponent } from '../list/usage-dossier.component';
import { UsageDossierDetailComponent } from '../detail/usage-dossier-detail.component';
import { UsageDossierUpdateComponent } from '../update/usage-dossier-update.component';
import { UsageDossierRoutingResolveService } from './usage-dossier-routing-resolve.service';

const usageDossierRoute: Routes = [
  {
    path: '',
    component: UsageDossierComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UsageDossierDetailComponent,
    resolve: {
      usageDossier: UsageDossierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UsageDossierUpdateComponent,
    resolve: {
      usageDossier: UsageDossierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UsageDossierUpdateComponent,
    resolve: {
      usageDossier: UsageDossierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(usageDossierRoute)],
  exports: [RouterModule],
})
export class UsageDossierRoutingModule {}
