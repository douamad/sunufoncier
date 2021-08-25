import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EvaluationClotureComponent } from './list/evaluation-cloture.component';
import { EvaluationClotureDetailComponent } from './detail/evaluation-cloture-detail.component';
import { EvaluationClotureUpdateComponent } from './update/evaluation-cloture-update.component';
import { EvaluationClotureDeleteDialogComponent } from './delete/evaluation-cloture-delete-dialog.component';
import { EvaluationClotureRoutingModule } from './route/evaluation-cloture-routing.module';

@NgModule({
  imports: [SharedModule, EvaluationClotureRoutingModule],
  declarations: [
    EvaluationClotureComponent,
    EvaluationClotureDetailComponent,
    EvaluationClotureUpdateComponent,
    EvaluationClotureDeleteDialogComponent,
  ],
  entryComponents: [EvaluationClotureDeleteDialogComponent],
})
export class EvaluationClotureModule {}
