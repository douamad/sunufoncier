import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { NatureComponent } from './list/nature.component';
import { NatureDetailComponent } from './detail/nature-detail.component';
import { NatureUpdateComponent } from './update/nature-update.component';
import { NatureDeleteDialogComponent } from './delete/nature-delete-dialog.component';
import { NatureRoutingModule } from './route/nature-routing.module';

@NgModule({
  imports: [SharedModule, NatureRoutingModule],
  declarations: [NatureComponent, NatureDetailComponent, NatureUpdateComponent, NatureDeleteDialogComponent],
  entryComponents: [NatureDeleteDialogComponent],
})
export class NatureModule {}
