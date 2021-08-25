import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocataire } from '../locataire.model';
import { LocataireService } from '../service/locataire.service';

@Component({
  templateUrl: './locataire-delete-dialog.component.html',
})
export class LocataireDeleteDialogComponent {
  locataire?: ILocataire;

  constructor(protected locataireService: LocataireService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.locataireService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
