import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { RefParcelaireComponent } from './list/ref-parcelaire.component';
import { RefParcelaireDetailComponent } from './detail/ref-parcelaire-detail.component';
import { RefParcelaireUpdateComponent } from './update/ref-parcelaire-update.component';
import { RefParcelaireDeleteDialogComponent } from './delete/ref-parcelaire-delete-dialog.component';
import { RefParcelaireRoutingModule } from './route/ref-parcelaire-routing.module';

@NgModule({
  imports: [SharedModule, RefParcelaireRoutingModule],
  declarations: [RefParcelaireComponent, RefParcelaireDetailComponent, RefParcelaireUpdateComponent, RefParcelaireDeleteDialogComponent],
  entryComponents: [RefParcelaireDeleteDialogComponent],
})
export class RefParcelaireModule {}
