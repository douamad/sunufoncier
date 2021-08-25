import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEvaluationBatiments } from '../evaluation-batiments.model';
import { EvaluationBatimentsService } from '../service/evaluation-batiments.service';

@Component({
  templateUrl: './evaluation-batiments-delete-dialog.component.html',
})
export class EvaluationBatimentsDeleteDialogComponent {
  evaluationBatiments?: IEvaluationBatiments;

  constructor(protected evaluationBatimentsService: EvaluationBatimentsService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluationBatimentsService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
