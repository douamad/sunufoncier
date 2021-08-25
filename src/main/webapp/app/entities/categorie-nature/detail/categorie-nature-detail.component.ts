import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieNature } from '../categorie-nature.model';

@Component({
  selector: 'jhi-categorie-nature-detail',
  templateUrl: './categorie-nature-detail.component.html',
})
export class CategorieNatureDetailComponent implements OnInit {
  categorieNature: ICategorieNature | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieNature }) => {
      this.categorieNature = categorieNature;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
