import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILotissement } from '../lotissement.model';

@Component({
  selector: 'jhi-lotissement-detail',
  templateUrl: './lotissement-detail.component.html',
})
export class LotissementDetailComponent implements OnInit {
  lotissement: ILotissement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lotissement }) => {
      this.lotissement = lotissement;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
