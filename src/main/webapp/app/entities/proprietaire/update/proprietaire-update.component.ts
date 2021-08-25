import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IProprietaire, Proprietaire } from '../proprietaire.model';
import { ProprietaireService } from '../service/proprietaire.service';

@Component({
  selector: 'jhi-proprietaire-update',
  templateUrl: './proprietaire-update.component.html',
})
export class ProprietaireUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    prenom: [],
    nom: [],
    situation: [],
    gestionDelegue: [],
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
    aquisition: [],
    statutPersoneStructure: [],
    typeStructure: [],
    nombreHeritiers: [],
    serviceOcupant: [],
    etablssement: [],
  });

  constructor(protected proprietaireService: ProprietaireService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proprietaire }) => {
      if (proprietaire.id === undefined) {
        const today = dayjs().startOf('day');
        proprietaire.dateNaiss = today;
      }

      this.updateForm(proprietaire);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proprietaire = this.createFromForm();
    if (proprietaire.id !== undefined) {
      this.subscribeToSaveResponse(this.proprietaireService.update(proprietaire));
    } else {
      this.subscribeToSaveResponse(this.proprietaireService.create(proprietaire));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProprietaire>>): void {
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

  protected updateForm(proprietaire: IProprietaire): void {
    this.editForm.patchValue({
      id: proprietaire.id,
      prenom: proprietaire.prenom,
      nom: proprietaire.nom,
      situation: proprietaire.situation,
      gestionDelegue: proprietaire.gestionDelegue,
      raisonSocial: proprietaire.raisonSocial,
      siegeSocial: proprietaire.siegeSocial,
      personneMorale: proprietaire.personneMorale,
      dateNaiss: proprietaire.dateNaiss ? proprietaire.dateNaiss.format(DATE_TIME_FORMAT) : null,
      lieuNaissance: proprietaire.lieuNaissance,
      numCNI: proprietaire.numCNI,
      ninea: proprietaire.ninea,
      adresse: proprietaire.adresse,
      email: proprietaire.email,
      telephone: proprietaire.telephone,
      telephone2: proprietaire.telephone2,
      telephone3: proprietaire.telephone3,
      aquisition: proprietaire.aquisition,
      statutPersoneStructure: proprietaire.statutPersoneStructure,
      typeStructure: proprietaire.typeStructure,
      nombreHeritiers: proprietaire.nombreHeritiers,
      serviceOcupant: proprietaire.serviceOcupant,
      etablssement: proprietaire.etablssement,
    });
  }
  protected createFromForm(): IProprietaire {
    return {
      ...new Proprietaire(),
      id: this.editForm.get(['id'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      situation: this.editForm.get(['situation'])!.value,
      gestionDelegue: this.editForm.get(['gestionDelegue'])!.value,
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
      aquisition: this.editForm.get(['aquisition'])!.value,
      statutPersoneStructure: this.editForm.get(['statutPersoneStructure'])!.value,
      typeStructure: this.editForm.get(['typeStructure'])!.value,
      nombreHeritiers: this.editForm.get(['nombreHeritiers'])!.value,
      serviceOcupant: this.editForm.get(['serviceOcupant'])!.value,
      etablssement: this.editForm.get(['etablssement'])!.value,
    };
  }
}
