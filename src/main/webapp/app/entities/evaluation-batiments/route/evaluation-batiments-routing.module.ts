import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EvaluationBatimentsComponent } from '../list/evaluation-batiments.component';
import { EvaluationBatimentsDetailComponent } from '../detail/evaluation-batiments-detail.component';
import { EvaluationBatimentsUpdateComponent } from '../update/evaluation-batiments-update.component';
import { EvaluationBatimentsRoutingResolveService } from './evaluation-batiments-routing-resolve.service';

const evaluationBatimentsRoute: Routes = [
  {
    path: '',
    component: EvaluationBatimentsComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EvaluationBatimentsDetailComponent,
    resolve: {
      evaluationBatiments: EvaluationBatimentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EvaluationBatimentsUpdateComponent,
    resolve: {
      evaluationBatiments: EvaluationBatimentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluationBatimentsUpdateComponent,
    resolve: {
      evaluationBatiments: EvaluationBatimentsRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(evaluationBatimentsRoute)],
  exports: [RouterModule],
})
export class EvaluationBatimentsRoutingModule {}
