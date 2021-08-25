import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ILocataire, Locataire } from '../locataire.model';
import { LocataireService } from '../service/locataire.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

@Component({
  selector: 'jhi-locataire-update',
  templateUrl: './locataire-update.component.html',
})
export class LocataireUpdateComponent implements OnInit {
  isSaving = false;

  dossiersSharedCollection: IDossier[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [],
    personne: [],
    activite: [],
    ninea: [],
    montant: [],
    dossier: [],
  });

  constructor(
    protected locataireService: LocataireService,
    protected dossierService: DossierService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ locataire }) => {
      this.updateForm(locataire);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const locataire = this.createFromForm();
    if (locataire.id !== undefined) {
      this.subscribeToSaveResponse(this.locataireService.update(locataire));
    } else {
      this.subscribeToSaveResponse(this.locataireService.create(locataire));
    }
  }

  trackDossierById(index: number, item: IDossier): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocataire>>): void {
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

  protected updateForm(locataire: ILocataire): void {
    this.editForm.patchValue({
      id: locataire.id,
      nom: locataire.nom,
      personne: locataire.personne,
      activite: locataire.activite,
      ninea: locataire.ninea,
      montant: locataire.montant,
      dossier: locataire.dossier,
    });

    this.dossiersSharedCollection = this.dossierService.addDossierToCollectionIfMissing(this.dossiersSharedCollection, locataire.dossier);
  }

  protected loadRelationshipsOptions(): void {
    this.dossierService
      .query()
      .pipe(map((res: HttpResponse<IDossier[]>) => res.body ?? []))
      .pipe(
        map((dossiers: IDossier[]) => this.dossierService.addDossierToCollectionIfMissing(dossiers, this.editForm.get('dossier')!.value))
      )
      .subscribe((dossiers: IDossier[]) => (this.dossiersSharedCollection = dossiers));
  }

  protected createFromForm(): ILocataire {
    return {
      ...new Locataire(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      personne: this.editForm.get(['personne'])!.value,
      activite: this.editForm.get(['activite'])!.value,
      ninea: this.editForm.get(['ninea'])!.value,
      montant: this.editForm.get(['montant'])!.value,
      dossier: this.editForm.get(['dossier'])!.value,
    };
  }
}
