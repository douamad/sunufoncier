import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRefParcelaire } from '../ref-parcelaire.model';
import { RefParcelaireService } from '../service/ref-parcelaire.service';

@Component({
  templateUrl: './ref-parcelaire-delete-dialog.component.html',
})
export class RefParcelaireDeleteDialogComponent {
  refParcelaire?: IRefParcelaire;

  constructor(protected refParcelaireService: RefParcelaireService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.refParcelaireService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
