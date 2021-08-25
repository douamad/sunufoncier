import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EvaluationBatimentsComponent } from './list/evaluation-batiments.component';
import { EvaluationBatimentsDetailComponent } from './detail/evaluation-batiments-detail.component';
import { EvaluationBatimentsUpdateComponent } from './update/evaluation-batiments-update.component';
import { EvaluationBatimentsDeleteDialogComponent } from './delete/evaluation-batiments-delete-dialog.component';
import { EvaluationBatimentsRoutingModule } from './route/evaluation-batiments-routing.module';

@NgModule({
  imports: [SharedModule, EvaluationBatimentsRoutingModule],
  declarations: [
    EvaluationBatimentsComponent,
    EvaluationBatimentsDetailComponent,
    EvaluationBatimentsUpdateComponent,
    EvaluationBatimentsDeleteDialogComponent,
  ],
  entryComponents: [EvaluationBatimentsDeleteDialogComponent],
})
export class EvaluationBatimentsModule {}
