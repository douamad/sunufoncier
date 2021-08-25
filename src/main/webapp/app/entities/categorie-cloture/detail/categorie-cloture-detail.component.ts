import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieCloture } from '../categorie-cloture.model';

@Component({
  selector: 'jhi-categorie-cloture-detail',
  templateUrl: './categorie-cloture-detail.component.html',
})
export class CategorieClotureDetailComponent implements OnInit {
  categorieCloture: ICategorieCloture | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieCloture }) => {
      this.categorieCloture = categorieCloture;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
