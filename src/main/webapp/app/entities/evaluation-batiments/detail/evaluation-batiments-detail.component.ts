import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluationBatiments } from '../evaluation-batiments.model';

@Component({
  selector: 'jhi-evaluation-batiments-detail',
  templateUrl: './evaluation-batiments-detail.component.html',
})
export class EvaluationBatimentsDetailComponent implements OnInit {
  evaluationBatiments: IEvaluationBatiments | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationBatiments }) => {
      this.evaluationBatiments = evaluationBatiments;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
