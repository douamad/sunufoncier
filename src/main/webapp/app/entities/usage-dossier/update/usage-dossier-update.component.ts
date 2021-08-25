import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IUsageDossier, UsageDossier } from '../usage-dossier.model';
import { UsageDossierService } from '../service/usage-dossier.service';

@Component({
  selector: 'jhi-usage-dossier-update',
  templateUrl: './usage-dossier-update.component.html',
})
export class UsageDossierUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
  });

  constructor(protected usageDossierService: UsageDossierService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usageDossier }) => {
      this.updateForm(usageDossier);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usageDossier = this.createFromForm();
    if (usageDossier.id !== undefined) {
      this.subscribeToSaveResponse(this.usageDossierService.update(usageDossier));
    } else {
      this.subscribeToSaveResponse(this.usageDossierService.create(usageDossier));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsageDossier>>): void {
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

  protected updateForm(usageDossier: IUsageDossier): void {
    this.editForm.patchValue({
      id: usageDossier.id,
      code: usageDossier.code,
      libelle: usageDossier.libelle,
    });
  }

  protected createFromForm(): IUsageDossier {
    return {
      ...new UsageDossier(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }
}
