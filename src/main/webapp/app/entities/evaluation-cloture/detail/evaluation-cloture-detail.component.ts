import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluationCloture } from '../evaluation-cloture.model';

@Component({
  selector: 'jhi-evaluation-cloture-detail',
  templateUrl: './evaluation-cloture-detail.component.html',
})
export class EvaluationClotureDetailComponent implements OnInit {
  evaluationCloture: IEvaluationCloture | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationCloture }) => {
      this.evaluationCloture = evaluationCloture;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
