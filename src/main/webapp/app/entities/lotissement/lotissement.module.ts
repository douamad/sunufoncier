import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { LotissementComponent } from './list/lotissement.component';
import { LotissementDetailComponent } from './detail/lotissement-detail.component';
import { LotissementUpdateComponent } from './update/lotissement-update.component';
import { LotissementDeleteDialogComponent } from './delete/lotissement-delete-dialog.component';
import { LotissementRoutingModule } from './route/lotissement-routing.module';

@NgModule({
  imports: [SharedModule, LotissementRoutingModule],
  declarations: [LotissementComponent, LotissementDetailComponent, LotissementUpdateComponent, LotissementDeleteDialogComponent],
  entryComponents: [LotissementDeleteDialogComponent],
})
export class LotissementModule {}
