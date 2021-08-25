import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieBatie } from '../categorie-batie.model';
import { CategorieBatieService } from '../service/categorie-batie.service';

@Component({
  templateUrl: './categorie-batie-delete-dialog.component.html',
})
export class CategorieBatieDeleteDialogComponent {
  categorieBatie?: ICategorieBatie;

  constructor(protected categorieBatieService: CategorieBatieService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieBatieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
