import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { LocataireComponent } from './list/locataire.component';
import { LocataireDetailComponent } from './detail/locataire-detail.component';
import { LocataireUpdateComponent } from './update/locataire-update.component';
import { LocataireDeleteDialogComponent } from './delete/locataire-delete-dialog.component';
import { LocataireRoutingModule } from './route/locataire-routing.module';

@NgModule({
  imports: [SharedModule, LocataireRoutingModule],
  declarations: [LocataireComponent, LocataireDetailComponent, LocataireUpdateComponent, LocataireDeleteDialogComponent],
  entryComponents: [LocataireDeleteDialogComponent],
})
export class LocataireModule {}
