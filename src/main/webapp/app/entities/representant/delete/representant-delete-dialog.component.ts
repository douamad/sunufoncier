import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRepresentant } from '../representant.model';
import { RepresentantService } from '../service/representant.service';

@Component({
  templateUrl: './representant-delete-dialog.component.html',
})
export class RepresentantDeleteDialogComponent {
  representant?: IRepresentant;

  constructor(protected representantService: RepresentantService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.representantService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
