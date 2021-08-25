import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEvaluationCloture, EvaluationCloture } from '../evaluation-cloture.model';
import { EvaluationClotureService } from '../service/evaluation-cloture.service';
import { ICategorieCloture } from 'app/entities/categorie-cloture/categorie-cloture.model';
import { CategorieClotureService } from 'app/entities/categorie-cloture/service/categorie-cloture.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

@Component({
  selector: 'jhi-evaluation-cloture-update',
  templateUrl: './evaluation-cloture-update.component.html',
})
export class EvaluationClotureUpdateComponent implements OnInit {
  isSaving = false;

  categorieCloturesSharedCollection: ICategorieCloture[] = [];
  dossiersSharedCollection: IDossier[] = [];

  editForm = this.fb.group({
    id: [],
    lineaire: [],
    coeff: [],
    categoriteCloture: [],
    dossier: [],
  });

  constructor(
    protected evaluationClotureService: EvaluationClotureService,
    protected categorieClotureService: CategorieClotureService,
    protected dossierService: DossierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationCloture }) => {
      this.updateForm(evaluationCloture);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evaluationCloture = this.createFromForm();
    if (evaluationCloture.id !== undefined) {
      this.subscribeToSaveResponse(this.evaluationClotureService.update(evaluationCloture));
    } else {
      this.subscribeToSaveResponse(this.evaluationClotureService.create(evaluationCloture));
    }
  }

  trackCategorieClotureById(index: number, item: ICategorieCloture): number {
    return item.id!;
  }

  trackDossierById(index: number, item: IDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluationCloture>>): void {
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

  protected updateForm(evaluationCloture: IEvaluationCloture): void {
    this.editForm.patchValue({
      id: evaluationCloture.id,
      lineaire: evaluationCloture.lineaire,
      coeff: evaluationCloture.coeff,
      categoriteCloture: evaluationCloture.categoriteCloture,
      dossier: evaluationCloture.dossier,
    });

    this.categorieCloturesSharedCollection = this.categorieClotureService.addCategorieClotureToCollectionIfMissing(
      this.categorieCloturesSharedCollection,
      evaluationCloture.categoriteCloture
    );
    this.dossiersSharedCollection = this.dossierService.addDossierToCollectionIfMissing(
      this.dossiersSharedCollection,
      evaluationCloture.dossier
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categorieClotureService
      .query()
      .pipe(map((res: HttpResponse<ICategorieCloture[]>) => res.body ?? []))
      .pipe(
        map((categorieClotures: ICategorieCloture[]) =>
          this.categorieClotureService.addCategorieClotureToCollectionIfMissing(
            categorieClotures,
            this.editForm.get('categoriteCloture')!.value
          )
        )
      )
      .subscribe((categorieClotures: ICategorieCloture[]) => (this.categorieCloturesSharedCollection = categorieClotures));

    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing(dossiers, this.editForm.get('dossier')!.value))
      )
      .subscribe((dossiers: IDossier[]) => (this.dossiersSharedCollection = dossiers));
  }

  protected createFromForm(): IEvaluationCloture {
    return {
      ...new EvaluationCloture(),
      id: this.editForm.get(['id'])!.value,
      lineaire: this.editForm.get(['lineaire'])!.value,
      coeff: this.editForm.get(['coeff'])!.value,
      categoriteCloture: this.editForm.get(['categoriteCloture'])!.value,
      dossier: this.editForm.get(['dossier'])!.value,
    };
  }
}
