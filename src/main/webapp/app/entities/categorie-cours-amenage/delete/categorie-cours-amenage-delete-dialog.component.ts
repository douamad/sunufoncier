import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieCoursAmenage } from '../categorie-cours-amenage.model';
import { CategorieCoursAmenageService } from '../service/categorie-cours-amenage.service';

@Component({
  templateUrl: './categorie-cours-amenage-delete-dialog.component.html',
})
export class CategorieCoursAmenageDeleteDialogComponent {
  categorieCoursAmenage?: ICategorieCoursAmenage;

  constructor(protected categorieCoursAmenageService: CategorieCoursAmenageService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieCoursAmenageService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
