import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICategorieCloture, CategorieCloture } from '../categorie-cloture.model';
import { CategorieClotureService } from '../service/categorie-cloture.service';

@Component({
  selector: 'jhi-categorie-cloture-update',
  templateUrl: './categorie-cloture-update.component.html',
})
export class CategorieClotureUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    prixMetreCare: [],
  });

  constructor(
    protected categorieClotureService: CategorieClotureService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieCloture }) => {
      this.updateForm(categorieCloture);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieCloture = this.createFromForm();
    if (categorieCloture.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieClotureService.update(categorieCloture));
    } else {
      this.subscribeToSaveResponse(this.categorieClotureService.create(categorieCloture));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieCloture>>): void {
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

  protected updateForm(categorieCloture: ICategorieCloture): void {
    this.editForm.patchValue({
      id: categorieCloture.id,
      libelle: categorieCloture.libelle,
      prixMetreCare: categorieCloture.prixMetreCare,
    });
  }

  protected createFromForm(): ICategorieCloture {
    return {
      ...new CategorieCloture(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      prixMetreCare: this.editForm.get(['prixMetreCare'])!.value,
    };
  }
}
