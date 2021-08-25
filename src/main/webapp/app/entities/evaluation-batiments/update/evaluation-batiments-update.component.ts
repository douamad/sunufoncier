import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEvaluationBatiments, EvaluationBatiments } from '../evaluation-batiments.model';
import { EvaluationBatimentsService } from '../service/evaluation-batiments.service';
import { ICategorieNature } from 'app/entities/categorie-nature/categorie-nature.model';
import { CategorieNatureService } from 'app/entities/categorie-nature/service/categorie-nature.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

@Component({
  selector: 'jhi-evaluation-batiments-update',
  templateUrl: './evaluation-batiments-update.component.html',
})
export class EvaluationBatimentsUpdateComponent implements OnInit {
  isSaving = false;

  categorieNaturesSharedCollection: ICategorieNature[] = [];
  dossiersSharedCollection: IDossier[] = [];

  editForm = this.fb.group({
    id: [],
    niveau: [],
    surface: [],
    coeff: [],
    categorieNature: [],
    dossier: [],
  });

  constructor(
    protected evaluationBatimentsService: EvaluationBatimentsService,
    protected categorieNatureService: CategorieNatureService,
    protected dossierService: DossierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationBatiments }) => {
      this.updateForm(evaluationBatiments);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evaluationBatiments = this.createFromForm();
    if (evaluationBatiments.id !== undefined) {
      this.subscribeToSaveResponse(this.evaluationBatimentsService.update(evaluationBatiments));
    } else {
      this.subscribeToSaveResponse(this.evaluationBatimentsService.create(evaluationBatiments));
    }
  }

  trackCategorieNatureById(index: number, item: ICategorieNature): number {
    return item.id!;
  }

  trackDossierById(index: number, item: IDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluationBatiments>>): void {
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

  protected updateForm(evaluationBatiments: IEvaluationBatiments): void {
    this.editForm.patchValue({
      id: evaluationBatiments.id,
      niveau: evaluationBatiments.niveau,
      surface: evaluationBatiments.surface,
      coeff: evaluationBatiments.coeff,
      categorieNature: evaluationBatiments.categorieNature,
      dossier: evaluationBatiments.dossier,
    });

    this.categorieNaturesSharedCollection = this.categorieNatureService.addCategorieNatureToCollectionIfMissing(
      this.categorieNaturesSharedCollection,
      evaluationBatiments.categorieNature
    );
    this.dossiersSharedCollection = this.dossierService.addDossierToCollectionIfMissing(
      this.dossiersSharedCollection,
      evaluationBatiments.dossier
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categorieNatureService
      .query()
      .pipe(map((res: HttpResponse<ICategorieNature[]>) => res.body ?? []))
      .pipe(
        map((categorieNatures: ICategorieNature[]) =>
          this.categorieNatureService.addCategorieNatureToCollectionIfMissing(categorieNatures, this.editForm.get('categorieNature')!.value)
        )
      )
      .subscribe((categorieNatures: ICategorieNature[]) => (this.categorieNaturesSharedCollection = categorieNatures));

    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing(dossiers, this.editForm.get('dossier')!.value))
      )
      .subscribe((dossiers: IDossier[]) => (this.dossiersSharedCollection = dossiers));
  }

  protected createFromForm(): IEvaluationBatiments {
    return {
      ...new EvaluationBatiments(),
      id: this.editForm.get(['id'])!.value,
      niveau: this.editForm.get(['niveau'])!.value,
      surface: this.editForm.get(['surface'])!.value,
      coeff: this.editForm.get(['coeff'])!.value,
      categorieNature: this.editForm.get(['categorieNature'])!.value,
      dossier: this.editForm.get(['dossier'])!.value,
    };
  }
}
