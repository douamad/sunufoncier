import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEvaluationSurfaceBatie, EvaluationSurfaceBatie } from '../evaluation-surface-batie.model';
import { EvaluationSurfaceBatieService } from '../service/evaluation-surface-batie.service';
import { ICategorieBatie } from 'app/entities/categorie-batie/categorie-batie.model';
import { CategorieBatieService } from 'app/entities/categorie-batie/service/categorie-batie.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

@Component({
  selector: 'jhi-evaluation-surface-batie-update',
  templateUrl: './evaluation-surface-batie-update.component.html',
})
export class EvaluationSurfaceBatieUpdateComponent implements OnInit {
  isSaving = false;

  categorieBatiesSharedCollection: ICategorieBatie[] = [];
  dossiersSharedCollection: IDossier[] = [];

  editForm = this.fb.group({
    id: [],
    superficieTotale: [],
    superficieBatie: [],
    categorieBatie: [],
    dossier: [],
  });

  constructor(
    protected evaluationSurfaceBatieService: EvaluationSurfaceBatieService,
    protected categorieBatieService: CategorieBatieService,
    protected dossierService: DossierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ evaluationSurfaceBatie }) => {
      this.updateForm(evaluationSurfaceBatie);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const evaluationSurfaceBatie = this.createFromForm();
    if (evaluationSurfaceBatie.id !== undefined) {
      this.subscribeToSaveResponse(this.evaluationSurfaceBatieService.update(evaluationSurfaceBatie));
    } else {
      this.subscribeToSaveResponse(this.evaluationSurfaceBatieService.create(evaluationSurfaceBatie));
    }
  }

  trackCategorieBatieById(index: number, item: ICategorieBatie): number {
    return item.id!;
  }

  trackDossierById(index: number, item: IDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvaluationSurfaceBatie>>): void {
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

  protected updateForm(evaluationSurfaceBatie: IEvaluationSurfaceBatie): void {
    this.editForm.patchValue({
      id: evaluationSurfaceBatie.id,
      superficieTotale: evaluationSurfaceBatie.superficieTotale,
      superficieBatie: evaluationSurfaceBatie.superficieBatie,
      categorieBatie: evaluationSurfaceBatie.categorieBatie,
      dossier: evaluationSurfaceBatie.dossier,
    });

    this.categorieBatiesSharedCollection = this.categorieBatieService.addCategorieBatieToCollectionIfMissing(
      this.categorieBatiesSharedCollection,
      evaluationSurfaceBatie.categorieBatie
    );
    this.dossiersSharedCollection = this.dossierService.addDossierToCollectionIfMissing(
      this.dossiersSharedCollection,
      evaluationSurfaceBatie.dossier
    );
  }

  protected loadRelationshipsOptions(): void {
    this.categorieBatieService
      .query()
      .pipe(map((res: HttpResponse<ICategorieBatie[]>) => res.body ?? []))
      .pipe(
        map((categorieBaties: ICategorieBatie[]) =>
          this.categorieBatieService.addCategorieBatieToCollectionIfMissing(categorieBaties, this.editForm.get('categorieBatie')!.value)
        )
      )
      .subscribe((categorieBaties: ICategorieBatie[]) => (this.categorieBatiesSharedCollection = categorieBaties));

    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing(dossiers, this.editForm.get('dossier')!.value))
      )
      .subscribe((dossiers: IDossier[]) => (this.dossiersSharedCollection = dossiers));
  }

  protected createFromForm(): IEvaluationSurfaceBatie {
    return {
      ...new EvaluationSurfaceBatie(),
      id: this.editForm.get(['id'])!.value,
      superficieTotale: this.editForm.get(['superficieTotale'])!.value,
      superficieBatie: this.editForm.get(['superficieBatie'])!.value,
      categorieBatie: this.editForm.get(['categorieBatie'])!.value,
      dossier: this.editForm.get(['dossier'])!.value,
    };
  }
}
