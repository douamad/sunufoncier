import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EvaluationClotureComponent } from '../list/evaluation-cloture.component';
import { EvaluationClotureDetailComponent } from '../detail/evaluation-cloture-detail.component';
import { EvaluationClotureUpdateComponent } from '../update/evaluation-cloture-update.component';
import { EvaluationClotureRoutingResolveService } from './evaluation-cloture-routing-resolve.service';

const evaluationClotureRoute: Routes = [
  {
    path: '',
    component: EvaluationClotureComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EvaluationClotureDetailComponent,
    resolve: {
      evaluationCloture: EvaluationClotureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EvaluationClotureUpdateComponent,
    resolve: {
      evaluationCloture: EvaluationClotureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluationClotureUpdateComponent,
    resolve: {
      evaluationCloture: EvaluationClotureRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(evaluationClotureRoute)],
  exports: [RouterModule],
})
export class EvaluationClotureRoutingModule {}
