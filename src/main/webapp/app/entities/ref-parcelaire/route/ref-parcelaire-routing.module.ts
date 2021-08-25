import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RefParcelaireComponent } from '../list/ref-parcelaire.component';
import { RefParcelaireDetailComponent } from '../detail/ref-parcelaire-detail.component';
import { RefParcelaireUpdateComponent } from '../update/ref-parcelaire-update.component';
import { RefParcelaireRoutingResolveService } from './ref-parcelaire-routing-resolve.service';

const refParcelaireRoute: Routes = [
  {
    path: '',
    component: RefParcelaireComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RefParcelaireDetailComponent,
    resolve: {
      refParcelaire: RefParcelaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RefParcelaireUpdateComponent,
    resolve: {
      refParcelaire: RefParcelaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RefParcelaireUpdateComponent,
    resolve: {
      refParcelaire: RefParcelaireRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(refParcelaireRoute)],
  exports: [RouterModule],
})
export class RefParcelaireRoutingModule {}
