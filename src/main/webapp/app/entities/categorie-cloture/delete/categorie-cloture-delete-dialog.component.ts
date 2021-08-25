import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieCloture } from '../categorie-cloture.model';
import { CategorieClotureService } from '../service/categorie-cloture.service';

@Component({
  templateUrl: './categorie-cloture-delete-dialog.component.html',
})
export class CategorieClotureDeleteDialogComponent {
  categorieCloture?: ICategorieCloture;

  constructor(protected categorieClotureService: CategorieClotureService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieClotureService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
