import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INature } from '../nature.model';
import { NatureService } from '../service/nature.service';

@Component({
  templateUrl: './nature-delete-dialog.component.html',
})
export class NatureDeleteDialogComponent {
  nature?: INature;

  constructor(protected natureService: NatureService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
