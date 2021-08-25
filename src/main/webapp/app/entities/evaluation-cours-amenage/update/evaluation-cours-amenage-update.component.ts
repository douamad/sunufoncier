import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEvaluationCoursAmenage, EvaluationCoursAmenage } from '../evaluation-cours-amenage.model';
import { EvaluationCoursAmenageService } from '../service/evaluation-cours-amenage.service';
import { ICategorieCoursAmenage } from 'app/entities/categorie-cours-amenage/categorie-cours-amenage.model';
import { CategorieCoursAmenageService } from 'app/entities/categorie-cours-amenage/service/categorie-cours-amenage.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

@Component({
  selector: 'jhi-evaluation-cours-amenage-update',
  templateUrl: './evaluation-cours-amenage-update.component.html',
})
export class EvaluationCoursAmenageUpdateComponent implements OnInit {
  isSaving = false;

  categorieCoursAmenagesSharedCollection: ICategorieCoursAmenage[] = [];
  dossiersSharedCollection: IDossier[] = [];

  editForm = this.fb.group({
    id: [],
    surface: [],
    coeff: [],
    categorieCoursAmenage: [],
    dossier: [],
  });

  constructor(
    protected evaluationCoursAmenageService: EvaluationCoursAmenageService,
    protected categorieCoursAmenageService: CategorieCoursAmenageService,
    protected dossierService: DossierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationCoursAmenage }) => {
      this.updateForm(evaluationCoursAmenage);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evaluationCoursAmenage = this.createFromForm();
    if (evaluationCoursAmenage.id !== undefined) {
      this.subscribeToSaveResponse(this.evaluationCoursAmenageService.update(evaluationCoursAmenage));
    } else {
      this.subscribeToSaveResponse(this.evaluationCoursAmenageService.create(evaluationCoursAmenage));
    }
  }

  trackCategorieCoursAmenageById(index: number, item: ICategorieCoursAmenage): number {
    return item.id!;
  }

  trackDossierById(index: number, item: IDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluationCoursAmenage>>): void {
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

  protected updateForm(evaluationCoursAmenage: IEvaluationCoursAmenage): void {
    this.editForm.patchValue({
      id: evaluationCoursAmenage.id,
      surface: evaluationCoursAmenage.surface,
      coeff: evaluationCoursAmenage.coeff,
      categorieCoursAmenage: evaluationCoursAmenage.categorieCoursAmenage,
      dossier: evaluationCoursAmenage.dossier,
    });

    this.categorieCoursAmenagesSharedCollection = this.categorieCoursAmenageService.addCategorieCoursAmenageToCollectionIfMissing(
      this.categorieCoursAmenagesSharedCollection,
      evaluationCoursAmenage.categorieCoursAmenage
    );
    this.dossiersSharedCollection = this.dossierService.addDossierToCollectionIfMissing(
      this.dossiersSharedCollection,
      evaluationCoursAmenage.dossier
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categorieCoursAmenageService
      .query()
      .pipe(map((res: HttpResponse<ICategorieCoursAmenage[]>) => res.body ?? []))
      .pipe(
        map((categorieCoursAmenages: ICategorieCoursAmenage[]) =>
          this.categorieCoursAmenageService.addCategorieCoursAmenageToCollectionIfMissing(
            categorieCoursAmenages,
            this.editForm.get('categorieCoursAmenage')!.value
          )
        )
      )
      .subscribe(
        (categorieCoursAmenages: ICategorieCoursAmenage[]) => (this.categorieCoursAmenagesSharedCollection = categorieCoursAmenages)
      );

    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing(dossiers, this.editForm.get('dossier')!.value))
      )
      .subscribe((dossiers: IDossier[]) => (this.dossiersSharedCollection = dossiers));
  }

  protected createFromForm(): IEvaluationCoursAmenage {
    return {
      ...new EvaluationCoursAmenage(),
      id: this.editForm.get(['id'])!.value,
      surface: this.editForm.get(['surface'])!.value,
      coeff: this.editForm.get(['coeff'])!.value,
      categorieCoursAmenage: this.editForm.get(['categorieCoursAmenage'])!.value,
      dossier: this.editForm.get(['dossier'])!.value,
    };
  }
}
