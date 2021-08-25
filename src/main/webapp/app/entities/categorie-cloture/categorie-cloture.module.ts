import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CategorieClotureComponent } from './list/categorie-cloture.component';
import { CategorieClotureDetailComponent } from './detail/categorie-cloture-detail.component';
import { CategorieClotureUpdateComponent } from './update/categorie-cloture-update.component';
import { CategorieClotureDeleteDialogComponent } from './delete/categorie-cloture-delete-dialog.component';
import { CategorieClotureRoutingModule } from './route/categorie-cloture-routing.module';

@NgModule({
  imports: [SharedModule, CategorieClotureRoutingModule],
  declarations: [
    CategorieClotureComponent,
    CategorieClotureDetailComponent,
    CategorieClotureUpdateComponent,
    CategorieClotureDeleteDialogComponent,
  ],
  entryComponents: [CategorieClotureDeleteDialogComponent],
})
export class CategorieClotureModule {}
