import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ILotissement } from '../lotissement.model';
import { LotissementService } from '../service/lotissement.service';

@Component({
  templateUrl: './lotissement-delete-dialog.component.html',
})
export class LotissementDeleteDialogComponent {
  lotissement?: ILotissement;

  constructor(protected lotissementService: LotissementService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.lotissementService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
