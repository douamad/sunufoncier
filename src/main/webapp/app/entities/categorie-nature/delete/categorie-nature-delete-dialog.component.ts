import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieNature } from '../categorie-nature.model';
import { CategorieNatureService } from '../service/categorie-nature.service';

@Component({
  templateUrl: './categorie-nature-delete-dialog.component.html',
})
export class CategorieNatureDeleteDialogComponent {
  categorieNature?: ICategorieNature;

  constructor(protected categorieNatureService: CategorieNatureService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieNatureService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
