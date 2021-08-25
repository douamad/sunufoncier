import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { UsageDossierComponent } from './list/usage-dossier.component';
import { UsageDossierDetailComponent } from './detail/usage-dossier-detail.component';
import { UsageDossierUpdateComponent } from './update/usage-dossier-update.component';
import { UsageDossierDeleteDialogComponent } from './delete/usage-dossier-delete-dialog.component';
import { UsageDossierRoutingModule } from './route/usage-dossier-routing.module';

@NgModule({
  imports: [SharedModule, UsageDossierRoutingModule],
  declarations: [UsageDossierComponent, UsageDossierDetailComponent, UsageDossierUpdateComponent, UsageDossierDeleteDialogComponent],
  entryComponents: [UsageDossierDeleteDialogComponent],
})
export class UsageDossierModule {}
