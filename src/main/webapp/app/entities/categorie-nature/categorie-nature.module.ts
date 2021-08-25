import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CategorieNatureComponent } from './list/categorie-nature.component';
import { CategorieNatureDetailComponent } from './detail/categorie-nature-detail.component';
import { CategorieNatureUpdateComponent } from './update/categorie-nature-update.component';
import { CategorieNatureDeleteDialogComponent } from './delete/categorie-nature-delete-dialog.component';
import { CategorieNatureRoutingModule } from './route/categorie-nature-routing.module';

@NgModule({
  imports: [SharedModule, CategorieNatureRoutingModule],
  declarations: [
    CategorieNatureComponent,
    CategorieNatureDetailComponent,
    CategorieNatureUpdateComponent,
    CategorieNatureDeleteDialogComponent,
  ],
  entryComponents: [CategorieNatureDeleteDialogComponent],
})
export class CategorieNatureModule {}
