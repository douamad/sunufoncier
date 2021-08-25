import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CategorieBatieComponent } from './list/categorie-batie.component';
import { CategorieBatieDetailComponent } from './detail/categorie-batie-detail.component';
import { CategorieBatieUpdateComponent } from './update/categorie-batie-update.component';
import { CategorieBatieDeleteDialogComponent } from './delete/categorie-batie-delete-dialog.component';
import { CategorieBatieRoutingModule } from './route/categorie-batie-routing.module';

@NgModule({
  imports: [SharedModule, CategorieBatieRoutingModule],
  declarations: [
    CategorieBatieComponent,
    CategorieBatieDetailComponent,
    CategorieBatieUpdateComponent,
    CategorieBatieDeleteDialogComponent,
  ],
  entryComponents: [CategorieBatieDeleteDialogComponent],
})
export class CategorieBatieModule {}
