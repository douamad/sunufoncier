import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EvaluationSurfaceBatieComponent } from './list/evaluation-surface-batie.component';
import { EvaluationSurfaceBatieDetailComponent } from './detail/evaluation-surface-batie-detail.component';
import { EvaluationSurfaceBatieUpdateComponent } from './update/evaluation-surface-batie-update.component';
import { EvaluationSurfaceBatieDeleteDialogComponent } from './delete/evaluation-surface-batie-delete-dialog.component';
import { EvaluationSurfaceBatieRoutingModule } from './route/evaluation-surface-batie-routing.module';

@NgModule({
  imports: [SharedModule, EvaluationSurfaceBatieRoutingModule],
  declarations: [
    EvaluationSurfaceBatieComponent,
    EvaluationSurfaceBatieDetailComponent,
    EvaluationSurfaceBatieUpdateComponent,
    EvaluationSurfaceBatieDeleteDialogComponent,
  ],
  entryComponents: [EvaluationSurfaceBatieDeleteDialogComponent],
})
export class EvaluationSurfaceBatieModule {}
