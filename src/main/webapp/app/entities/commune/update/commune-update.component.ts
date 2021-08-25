import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICommune, Commune } from '../commune.model';
import { CommuneService } from '../service/commune.service';
import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';
import { ArrondissementService } from 'app/entities/arrondissement/service/arrondissement.service';

@Component({
  selector: 'jhi-commune-update',
  templateUrl: './commune-update.component.html',
})
export class CommuneUpdateComponent implements OnInit {
  isSaving = false;

  arrondissementsSharedCollection: IArrondissement[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    arrondissement: [],
  });

  constructor(
    protected communeService: CommuneService,
    protected arrondissementService: ArrondissementService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commune }) => {
      this.updateForm(commune);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commune = this.createFromForm();
    if (commune.id !== undefined) {
      this.subscribeToSaveResponse(this.communeService.update(commune));
    } else {
      this.subscribeToSaveResponse(this.communeService.create(commune));
    }
  }

  trackArrondissementById(index: number, item: IArrondissement): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommune>>): void {
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

  protected updateForm(commune: ICommune): void {
    this.editForm.patchValue({
      id: commune.id,
      code: commune.code,
      libelle: commune.libelle,
      arrondissement: commune.arrondissement,
    });

    this.arrondissementsSharedCollection = this.arrondissementService.addArrondissementToCollectionIfMissing(
      this.arrondissementsSharedCollection,
      commune.arrondissement
    );
  }

  protected loadRelationshipsOptions(): void {
    this.arrondissementService
      .query()
      .pipe(map((res: HttpResponse<IArrondissement[]>) => res.body ?? []))
      .pipe(
        map((arrondissements: IArrondissement[]) =>
          this.arrondissementService.addArrondissementToCollectionIfMissing(arrondissements, this.editForm.get('arrondissement')!.value)
        )
      )
      .subscribe((arrondissements: IArrondissement[]) => (this.arrondissementsSharedCollection = arrondissements));
  }

  protected createFromForm(): ICommune {
    return {
      ...new Commune(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      arrondissement: this.editForm.get(['arrondissement'])!.value,
    };
  }
}
