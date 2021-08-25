import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { RefcadastraleComponent } from './list/refcadastrale.component';
import { RefcadastraleDetailComponent } from './detail/refcadastrale-detail.component';
import { RefcadastraleUpdateComponent } from './update/refcadastrale-update.component';
import { RefcadastraleDeleteDialogComponent } from './delete/refcadastrale-delete-dialog.component';
import { RefcadastraleRoutingModule } from './route/refcadastrale-routing.module';

@NgModule({
  imports: [SharedModule, RefcadastraleRoutingModule],
  declarations: [RefcadastraleComponent, RefcadastraleDetailComponent, RefcadastraleUpdateComponent, RefcadastraleDeleteDialogComponent],
  entryComponents: [RefcadastraleDeleteDialogComponent],
})
export class RefcadastraleModule {}
