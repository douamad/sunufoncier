import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieCoursAmenage } from '../categorie-cours-amenage.model';

@Component({
  selector: 'jhi-categorie-cours-amenage-detail',
  templateUrl: './categorie-cours-amenage-detail.component.html',
})
export class CategorieCoursAmenageDetailComponent implements OnInit {
  categorieCoursAmenage: ICategorieCoursAmenage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieCoursAmenage }) => {
      this.categorieCoursAmenage = categorieCoursAmenage;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
