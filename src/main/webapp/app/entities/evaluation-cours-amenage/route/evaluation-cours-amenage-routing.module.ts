import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EvaluationCoursAmenageComponent } from '../list/evaluation-cours-amenage.component';
import { EvaluationCoursAmenageDetailComponent } from '../detail/evaluation-cours-amenage-detail.component';
import { EvaluationCoursAmenageUpdateComponent } from '../update/evaluation-cours-amenage-update.component';
import { EvaluationCoursAmenageRoutingResolveService } from './evaluation-cours-amenage-routing-resolve.service';

const evaluationCoursAmenageRoute: Routes = [
  {
    path: '',
    component: EvaluationCoursAmenageComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EvaluationCoursAmenageDetailComponent,
    resolve: {
      evaluationCoursAmenage: EvaluationCoursAmenageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EvaluationCoursAmenageUpdateComponent,
    resolve: {
      evaluationCoursAmenage: EvaluationCoursAmenageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluationCoursAmenageUpdateComponent,
    resolve: {
      evaluationCoursAmenage: EvaluationCoursAmenageRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(evaluationCoursAmenageRoute)],
  exports: [RouterModule],
})
export class EvaluationCoursAmenageRoutingModule {}
