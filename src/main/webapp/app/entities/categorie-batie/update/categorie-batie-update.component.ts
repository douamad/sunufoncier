import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICategorieBatie, CategorieBatie } from '../categorie-batie.model';
import { CategorieBatieService } from '../service/categorie-batie.service';

@Component({
  selector: 'jhi-categorie-batie-update',
  templateUrl: './categorie-batie-update.component.html',
})
export class CategorieBatieUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelle: [],
    prixMetreCare: [],
  });

  constructor(
    protected categorieBatieService: CategorieBatieService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorieBatie }) => {
      this.updateForm(categorieBatie);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorieBatie = this.createFromForm();
    if (categorieBatie.id !== undefined) {
      this.subscribeToSaveResponse(this.categorieBatieService.update(categorieBatie));
    } else {
      this.subscribeToSaveResponse(this.categorieBatieService.create(categorieBatie));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorieBatie>>): void {
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

  protected updateForm(categorieBatie: ICategorieBatie): void {
    this.editForm.patchValue({
      id: categorieBatie.id,
      libelle: categorieBatie.libelle,
      prixMetreCare: categorieBatie.prixMetreCare,
    });
  }

  protected createFromForm(): ICategorieBatie {
    return {
      ...new CategorieBatie(),
      id: this.editForm.get(['id'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      prixMetreCare: this.editForm.get(['prixMetreCare'])!.value,
    };
  }
}
