import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvaluationCoursAmenage } from '../evaluation-cours-amenage.model';

@Component({
  selector: 'jhi-evaluation-cours-amenage-detail',
  templateUrl: './evaluation-cours-amenage-detail.component.html',
})
export class EvaluationCoursAmenageDetailComponent implements OnInit {
  evaluationCoursAmenage: IEvaluationCoursAmenage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationCoursAmenage }) => {
      this.evaluationCoursAmenage = evaluationCoursAmenage;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
