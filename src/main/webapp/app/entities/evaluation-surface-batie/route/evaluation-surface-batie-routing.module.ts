import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EvaluationSurfaceBatieComponent } from '../list/evaluation-surface-batie.component';
import { EvaluationSurfaceBatieDetailComponent } from '../detail/evaluation-surface-batie-detail.component';
import { EvaluationSurfaceBatieUpdateComponent } from '../update/evaluation-surface-batie-update.component';
import { EvaluationSurfaceBatieRoutingResolveService } from './evaluation-surface-batie-routing-resolve.service';

const evaluationSurfaceBatieRoute: Routes = [
  {
    path: '',
    component: EvaluationSurfaceBatieComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EvaluationSurfaceBatieDetailComponent,
    resolve: {
      evaluationSurfaceBatie: EvaluationSurfaceBatieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EvaluationSurfaceBatieUpdateComponent,
    resolve: {
      evaluationSurfaceBatie: EvaluationSurfaceBatieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EvaluationSurfaceBatieUpdateComponent,
    resolve: {
      evaluationSurfaceBatie: EvaluationSurfaceBatieRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(evaluationSurfaceBatieRoute)],
  exports: [RouterModule],
})
export class EvaluationSurfaceBatieRoutingModule {}
