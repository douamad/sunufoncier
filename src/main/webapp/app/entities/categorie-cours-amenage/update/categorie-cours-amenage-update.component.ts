import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICategorieCoursAmenage, CategorieCoursAmenage } from '../categorie-cours-amenage.model';
import { CategorieCoursAmenageService } from '../service/categorie-cours-amenage.service';

@Component({
  selector: 'jhi-categorie-cours-amenage-update',
  templateUrl: './categorie-cours-amenage-update.component.html',
})
export class CategorieCoursAmenageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    prixMetreCare: [],
  });

  constructor(
    protected categorieCoursAmenageService: CategorieCoursAmenageService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieCoursAmenage }) => {
      this.updateForm(categorieCoursAmenage);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieCoursAmenage = this.createFromForm();
    if (categorieCoursAmenage.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieCoursAmenageService.update(categorieCoursAmenage));
    } else {
      this.subscribeToSaveResponse(this.categorieCoursAmenageService.create(categorieCoursAmenage));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieCoursAmenage>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(categorieCoursAmenage: ICategorieCoursAmenage): void {
    this.editForm.patchValue({
      id: categorieCoursAmenage.id,
      libelle: categorieCoursAmenage.libelle,
      prixMetreCare: categorieCoursAmenage.prixMetreCare,
    });
  }

  protected createFromForm(): ICategorieCoursAmenage {
    return {
      ...new CategorieCoursAmenage(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      prixMetreCare: this.editForm.get(['prixMetreCare'])!.value,
    };
  }
}
