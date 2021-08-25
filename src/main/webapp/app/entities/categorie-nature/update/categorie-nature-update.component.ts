import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICategorieNature, CategorieNature } from '../categorie-nature.model';
import { CategorieNatureService } from '../service/categorie-nature.service';

@Component({
  selector: 'jhi-categorie-nature-update',
  templateUrl: './categorie-nature-update.component.html',
})
export class CategorieNatureUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nature: [],
    libelle: [],
    prixMetreCare: [],
  });

  constructor(
    protected categorieNatureService: CategorieNatureService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieNature }) => {
      this.updateForm(categorieNature);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieNature = this.createFromForm();
    if (categorieNature.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieNatureService.update(categorieNature));
    } else {
      this.subscribeToSaveResponse(this.categorieNatureService.create(categorieNature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieNature>>): void {
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

  protected updateForm(categorieNature: ICategorieNature): void {
    this.editForm.patchValue({
      id: categorieNature.id,
      nature: categorieNature.nature,
      libelle: categorieNature.libelle,
      prixMetreCare: categorieNature.prixMetreCare,
    });
  }

  protected createFromForm(): ICategorieNature {
    return {
      ...new CategorieNature(),
      id: this.editForm.get(['id'])!.value,
      nature: this.editForm.get(['nature'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      prixMetreCare: this.editForm.get(['prixMetreCare'])!.value,
    };
  }
}
