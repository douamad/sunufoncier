import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IQuartier, Quartier } from '../quartier.model';
import { QuartierService } from '../service/quartier.service';
import { ICommune } from 'app/entities/commune/commune.model';
import { CommuneService } from 'app/entities/commune/service/commune.service';

@Component({
  selector: 'jhi-quartier-update',
  templateUrl: './quartier-update.component.html',
})
export class QuartierUpdateComponent implements OnInit {
  isSaving = false;

  communesSharedCollection: ICommune[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    communune: [],
  });

  constructor(
    protected quartierService: QuartierService,
    protected communeService: CommuneService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ quartier }) => {
      this.updateForm(quartier);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const quartier = this.createFromForm();
    if (quartier.id !== undefined) {
      this.subscribeToSaveResponse(this.quartierService.update(quartier));
    } else {
      this.subscribeToSaveResponse(this.quartierService.create(quartier));
    }
  }

  trackCommuneById(index: number, item: ICommune): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuartier>>): void {
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

  protected updateForm(quartier: IQuartier): void {
    this.editForm.patchValue({
      id: quartier.id,
      code: quartier.code,
      libelle: quartier.libelle,
      communune: quartier.communune,
    });

    this.communesSharedCollection = this.communeService.addCommuneToCollectionIfMissing(this.communesSharedCollection, quartier.communune);
  }

  protected loadRelationshipsOptions(): void {
    this.communeService
      .query()
      .pipe(map((res: HttpResponse<ICommune[]>) => res.body ?? []))
      .pipe(
        map((communes: ICommune[]) => this.communeService.addCommuneToCollectionIfMissing(communes, this.editForm.get('communune')!.value))
      )
      .subscribe((communes: ICommune[]) => (this.communesSharedCollection = communes));
  }

  protected createFromForm(): IQuartier {
    return {
      ...new Quartier(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      communune: this.editForm.get(['communune'])!.value,
    };
  }
}
