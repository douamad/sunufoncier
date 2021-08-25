import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { LocataireComponent } from '../list/locataire.component';
import { LocataireDetailComponent } from '../detail/locataire-detail.component';
import { LocataireUpdateComponent } from '../update/locataire-update.component';
import { LocataireRoutingResolveService } from './locataire-routing-resolve.service';

const locataireRoute: Routes = [
  {
    path: '',
    component: LocataireComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LocataireDetailComponent,
    resolve: {
      locataire: LocataireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LocataireUpdateComponent,
    resolve: {
      locataire: LocataireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LocataireUpdateComponent,
    resolve: {
      locataire: LocataireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(locataireRoute)],
  exports: [RouterModule],
})
export class LocataireRoutingModule {}
