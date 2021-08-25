import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRepresentant, Representant } from '../representant.model';
import { RepresentantService } from '../service/representant.service';
import { IProprietaire } from 'app/entities/proprietaire/proprietaire.model';
import { ProprietaireService } from 'app/entities/proprietaire/service/proprietaire.service';

@Component({
  selector: 'jhi-representant-update',
  templateUrl: './representant-update.component.html',
})
export class RepresentantUpdateComponent implements OnInit {
  isSaving = false;

  proprietairesSharedCollection: IProprietaire[] = [];

  editForm = this.fb.group({
    id: [],
    prenom: [],
    lienProprietaire: [],
    nom: [],
    actif: [],
    raisonSocial: [],
    siegeSocial: [],
    personneMorale: [],
    dateNaiss: [],
    lieuNaissance: [],
    numCNI: [],
    ninea: [],
    adresse: [],
    email: [],
    telephone: [],
    telephone2: [],
    telephone3: [],
    statutPersoneStructure: [],
    typeStructure: [],
    proprietaire: [],
  });

  constructor(
    protected representantService: RepresentantService,
    protected proprietaireService: ProprietaireService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ representant }) => {
      if (representant.id === undefined) {
        const today = dayjs().startOf('day');
        representant.dateNaiss = today;
      }

      this.updateForm(representant);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const representant = this.createFromForm();
    if (representant.id !== undefined) {
      this.subscribeToSaveResponse(this.representantService.update(representant));
    } else {
      this.subscribeToSaveResponse(this.representantService.create(representant));
    }
  }

  trackProprietaireById(index: number, item: IProprietaire): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRepresentant>>): void {
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

  protected updateForm(representant: IRepresentant): void {
    this.editForm.patchValue({
      id: representant.id,
      prenom: representant.prenom,
      lienProprietaire: representant.lienProprietaire,
      nom: representant.nom,
      actif: representant.actif,
      raisonSocial: representant.raisonSocial,
      siegeSocial: representant.siegeSocial,
      personneMorale: representant.personneMorale,
      dateNaiss: representant.dateNaiss ? representant.dateNaiss.format(DATE_TIME_FORMAT) : null,
      lieuNaissance: representant.lieuNaissance,
      numCNI: representant.numCNI,
      ninea: representant.ninea,
      adresse: representant.adresse,
      email: representant.email,
      telephone: representant.telephone,
      telephone2: representant.telephone2,
      telephone3: representant.telephone3,
      statutPersoneStructure: representant.statutPersoneStructure,
      typeStructure: representant.typeStructure,
      proprietaire: representant.proprietaire,
    });

    this.proprietairesSharedCollection = this.proprietaireService.addProprietaireToCollectionIfMissing(
      this.proprietairesSharedCollection,
      representant.proprietaire
    );
  }

  protected loadRelationshipsOptions(): void {
    this.proprietaireService
      .query()
      .pipe(map((res: HttpResponse<IProprietaire[]>) => res.body ?? []))
      .pipe(
        map((proprietaires: IProprietaire[]) =>
          this.proprietaireService.addProprietaireToCollectionIfMissing(proprietaires, this.editForm.get('proprietaire')!.value)
        )
      )
      .subscribe((proprietaires: IProprietaire[]) => (this.proprietairesSharedCollection = proprietaires));
  }

  protected createFromForm(): IRepresentant {
    return {
      ...new Representant(),
      id: this.editForm.get(['id'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      lienProprietaire: this.editForm.get(['lienProprietaire'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      actif: this.editForm.get(['actif'])!.value,
      raisonSocial: this.editForm.get(['raisonSocial'])!.value,
      siegeSocial: this.editForm.get(['siegeSocial'])!.value,
      personneMorale: this.editForm.get(['personneMorale'])!.value,
      dateNaiss: this.editForm.get(['dateNaiss'])!.value ? dayjs(this.editForm.get(['dateNaiss'])!.value, DATE_TIME_FORMAT) : undefined,
      lieuNaissance: this.editForm.get(['lieuNaissance'])!.value,
      numCNI: this.editForm.get(['numCNI'])!.value,
      ninea: this.editForm.get(['ninea'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      email: this.editForm.get(['email'])!.value,
      telephone: this.editForm.get(['telephone'])!.value,
      telephone2: this.editForm.get(['telephone2'])!.value,
      telephone3: this.editForm.get(['telephone3'])!.value,
      statutPersoneStructure: this.editForm.get(['statutPersoneStructure'])!.value,
      typeStructure: this.editForm.get(['typeStructure'])!.value,
      proprietaire: this.editForm.get(['proprietaire'])!.value,
    };
  }
}
