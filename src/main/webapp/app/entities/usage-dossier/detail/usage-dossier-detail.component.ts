import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUsageDossier } from '../usage-dossier.model';

@Component({
  selector: 'jhi-usage-dossier-detail',
  templateUrl: './usage-dossier-detail.component.html',
})
export class UsageDossierDetailComponent implements OnInit {
  usageDossier: IUsageDossier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usageDossier }) => {
      this.usageDossier = usageDossier;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
