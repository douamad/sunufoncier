import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEvaluationSurfaceBatie } from '../evaluation-surface-batie.model';
import { EvaluationSurfaceBatieService } from '../service/evaluation-surface-batie.service';

@Component({
  templateUrl: './evaluation-surface-batie-delete-dialog.component.html',
})
export class EvaluationSurfaceBatieDeleteDialogComponent {
  evaluationSurfaceBatie?: IEvaluationSurfaceBatie;

  constructor(protected evaluationSurfaceBatieService: EvaluationSurfaceBatieService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.evaluationSurfaceBatieService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
