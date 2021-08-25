import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieBatie } from '../categorie-batie.model';

@Component({
  selector: 'jhi-categorie-batie-detail',
  templateUrl: './categorie-batie-detail.component.html',
})
export class CategorieBatieDetailComponent implements OnInit {
  categorieBatie: ICategorieBatie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieBatie }) => {
      this.categorieBatie = categorieBatie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
