import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INature, Nature } from '../nature.model';
import { NatureService } from '../service/nature.service';

@Component({
  selector: 'jhi-nature-update',
  templateUrl: './nature-update.component.html',
})
export class NatureUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    libelle: [],
  });

  constructor(protected natureService: NatureService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nature }) => {
      this.updateForm(nature);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nature = this.createFromForm();
    if (nature.id !== undefined) {
      this.subscribeToSaveResponse(this.natureService.update(nature));
    } else {
      this.subscribeToSaveResponse(this.natureService.create(nature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INature>>): void {
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

  protected updateForm(nature: INature): void {
    this.editForm.patchValue({
      id: nature.id,
      code: nature.code,
      libelle: nature.libelle,
    });
  }

  protected createFromForm(): INature {
    return {
      ...new Nature(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      libelle: this.editForm.get(['libelle'])!.value,
    };
  }
}
