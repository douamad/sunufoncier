import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EvaluationCoursAmenageComponent } from './list/evaluation-cours-amenage.component';
import { EvaluationCoursAmenageDetailComponent } from './detail/evaluation-cours-amenage-detail.component';
import { EvaluationCoursAmenageUpdateComponent } from './update/evaluation-cours-amenage-update.component';
import { EvaluationCoursAmenageDeleteDialogComponent } from './delete/evaluation-cours-amenage-delete-dialog.component';
import { EvaluationCoursAmenageRoutingModule } from './route/evaluation-cours-amenage-routing.module';

@NgModule({
  imports: [SharedModule, EvaluationCoursAmenageRoutingModule],
  declarations: [
    EvaluationCoursAmenageComponent,
    EvaluationCoursAmenageDetailComponent,
    EvaluationCoursAmenageUpdateComponent,
    EvaluationCoursAmenageDeleteDialogComponent,
  ],
  entryComponents: [EvaluationCoursAmenageDeleteDialogComponent],
})
export class EvaluationCoursAmenageModule {}
