import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CategorieCoursAmenageComponent } from './list/categorie-cours-amenage.component';
import { CategorieCoursAmenageDetailComponent } from './detail/categorie-cours-amenage-detail.component';
import { CategorieCoursAmenageUpdateComponent } from './update/categorie-cours-amenage-update.component';
import { CategorieCoursAmenageDeleteDialogComponent } from './delete/categorie-cours-amenage-delete-dialog.component';
import { CategorieCoursAmenageRoutingModule } from './route/categorie-cours-amenage-routing.module';

@NgModule({
  imports: [SharedModule, CategorieCoursAmenageRoutingModule],
  declarations: [
    CategorieCoursAmenageComponent,
    CategorieCoursAmenageDetailComponent,
    CategorieCoursAmenageUpdateComponent,
    CategorieCoursAmenageDeleteDialogComponent,
  ],
  entryComponents: [CategorieCoursAmenageDeleteDialogComponent],
})
export class CategorieCoursAmenageModule {}
