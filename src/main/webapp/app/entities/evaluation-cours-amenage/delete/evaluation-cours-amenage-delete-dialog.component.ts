import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEvaluationCoursAmenage } from '../evaluation-cours-amenage.model';
import { EvaluationCoursAmenageService } from '../service/evaluation-cours-amenage.service';

@Component({
  templateUrl: './evaluation-cours-amenage-delete-dialog.component.html',
})
export class EvaluationCoursAmenageDeleteDialogComponent {
  evaluationCoursAmenage?: IEvaluationCoursAmenage;

  constructor(protected evaluationCoursAmenageService: EvaluationCoursAmenageService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluationCoursAmenageService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
