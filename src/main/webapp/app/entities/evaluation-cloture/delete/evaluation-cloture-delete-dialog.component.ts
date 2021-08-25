import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEvaluationCloture } from '../evaluation-cloture.model';
import { EvaluationClotureService } from '../service/evaluation-cloture.service';

@Component({
  templateUrl: './evaluation-cloture-delete-dialog.component.html',
})
export class EvaluationClotureDeleteDialogComponent {
  evaluationCloture?: IEvaluationCloture;

  constructor(protected evaluationClotureService: EvaluationClotureService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluationClotureService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
