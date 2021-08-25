import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluationSurfaceBatie } from '../evaluation-surface-batie.model';

@Component({
  selector: 'jhi-evaluation-surface-batie-detail',
  templateUrl: './evaluation-surface-batie-detail.component.html',
})
export class EvaluationSurfaceBatieDetailComponent implements OnInit {
  evaluationSurfaceBatie: IEvaluationSurfaceBatie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationSurfaceBatie }) => {
      this.evaluationSurfaceBatie = evaluationSurfaceBatie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
