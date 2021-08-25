import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILotissement, Lotissement } from '../lotissement.model';
import { LotissementService } from '../service/lotissement.service';
import { IQuartier } from 'app/entities/quartier/quartier.model';
import { QuartierService } from 'app/entities/quartier/service/quartier.service';

@Component({
  selector: 'jhi-lotissement-update',
  templateUrl: './lotissement-update.component.html',
})
export class LotissementUpdateComponent implements OnInit {
  isSaving = false;

  quartiersSharedCollection: IQuartier[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
    quartier: [],
  });

  constructor(
    protected lotissementService: LotissementService,
    protected quartierService: QuartierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lotissement }) => {
      this.updateForm(lotissement);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lotissement = this.createFromForm();
    if (lotissement.id !== undefined) {
      this.subscribeToSaveResponse(this.lotissementService.update(lotissement));
    } else {
      this.subscribeToSaveResponse(this.lotissementService.create(lotissement));
    }
  }

  trackQuartierById(index: number, item: IQuartier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILotissement>>): void {
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

  protected updateForm(lotissement: ILotissement): void {
    this.editForm.patchValue({
      id: lotissement.id,
      code: lotissement.code,
      libelle: lotissement.libelle,
      quartier: lotissement.quartier,
    });

    this.quartiersSharedCollection = this.quartierService.addQuartierToCollectionIfMissing(
      this.quartiersSharedCollection,
      lotissement.quartier
    );
  }

  protected loadRelationshipsOptions(): void {
    this.quartierService
      .query()
      .pipe(map((res: HttpResponse<IQuartier[]>) => res.body ?? []))
      .pipe(
        map((quartiers: IQuartier[]) =>
          this.quartierService.addQuartierToCollectionIfMissing(quartiers, this.editForm.get('quartier')!.value)
        )
      )
      .subscribe((quartiers: IQuartier[]) => (this.quartiersSharedCollection = quartiers));
  }

  protected createFromForm(): ILotissement {
    return {
      ...new Lotissement(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
      quartier: this.editForm.get(['quartier'])!.value,
    };
  }
}
