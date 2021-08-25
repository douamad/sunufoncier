import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsageDossier } from '../usage-dossier.model';
import { UsageDossierService } from '../service/usage-dossier.service';

@Component({
  templateUrl: './usage-dossier-delete-dialog.component.html',
})
export class UsageDossierDeleteDialogComponent {
  usageDossier?: IUsageDossier;

  constructor(protected usageDossierService: UsageDossierService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.usageDossierService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
