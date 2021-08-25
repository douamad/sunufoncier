import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRefParcelaire, RefParcelaire } from '../ref-parcelaire.model';
import { RefParcelaireService } from '../service/ref-parcelaire.service';
import { ICommune } from 'app/entities/commune/commune.model';
import { CommuneService } from 'app/entities/commune/service/commune.service';

@Component({
  selector: 'jhi-ref-parcelaire-update',
  templateUrl: './ref-parcelaire-update.component.html',
})
export class RefParcelaireUpdateComponent implements OnInit {
  isSaving = false;

  communesSharedCollection: ICommune[] = [];

  editForm = this.fb.group({
    id: [],
    numeroParcelle: [],
    natureParcelle: [],
    batie: [],
    commune: [],
  });

  constructor(
    protected refParcelaireService: RefParcelaireService,
    protected communeService: CommuneService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refParcelaire }) => {
      this.updateForm(refParcelaire);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const refParcelaire = this.createFromForm();
    if (refParcelaire.id !== undefined) {
      this.subscribeToSaveResponse(this.refParcelaireService.update(refParcelaire));
    } else {
      this.subscribeToSaveResponse(this.refParcelaireService.create(refParcelaire));
    }
  }

  trackCommuneById(index: number, item: ICommune): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefParcelaire>>): void {
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

  protected updateForm(refParcelaire: IRefParcelaire): void {
    this.editForm.patchValue({
      id: refParcelaire.id,
      numeroParcelle: refParcelaire.numeroParcelle,
      natureParcelle: refParcelaire.natureParcelle,
      batie: refParcelaire.batie,
      commune: refParcelaire.commune,
    });

    this.communesSharedCollection = this.communeService.addCommuneToCollectionIfMissing(
      this.communesSharedCollection,
      refParcelaire.commune
    );
  }

  protected loadRelationshipsOptions(): void {
    this.communeService
      .query()
      .pipe(map((res: HttpResponse<ICommune[]>) => res.body ?? []))
      .pipe(
        map((communes: ICommune[]) => this.communeService.addCommuneToCollectionIfMissing(communes, this.editForm.get('commune')!.value))
      )
      .subscribe((communes: ICommune[]) => (this.communesSharedCollection = communes));
  }

  protected createFromForm(): IRefParcelaire {
    return {
      ...new RefParcelaire(),
      id: this.editForm.get(['id'])!.value,
      numeroParcelle: this.editForm.get(['numeroParcelle'])!.value,
      natureParcelle: this.editForm.get(['natureParcelle'])!.value,
      batie: this.editForm.get(['batie'])!.value,
      commune: this.editForm.get(['commune'])!.value,
    };
  }
}
