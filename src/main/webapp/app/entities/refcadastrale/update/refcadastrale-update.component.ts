import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IRefcadastrale, Refcadastrale } from '../refcadastrale.model';
import { RefcadastraleService } from '../service/refcadastrale.service';

@Component({
  selector: 'jhi-refcadastrale-update',
  templateUrl: './refcadastrale-update.component.html',
})
export class RefcadastraleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codeSection: [],
    codeParcelle: [],
    nicad: [],
    superfici: [],
    titreMere: [],
    titreCree: [],
    numeroRequisition: [],
    dateBornage: [],
  });

  constructor(protected refcadastraleService: RefcadastraleService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refcadastrale }) => {
      if (refcadastrale.id === undefined) {
        const today = dayjs().startOf('day');
        refcadastrale.dateBornage = today;
      }

      this.updateForm(refcadastrale);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const refcadastrale = this.createFromForm();
    if (refcadastrale.id !== undefined) {
      this.subscribeToSaveResponse(this.refcadastraleService.update(refcadastrale));
    } else {
      this.subscribeToSaveResponse(this.refcadastraleService.create(refcadastrale));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefcadastrale>>): void {
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

  protected updateForm(refcadastrale: IRefcadastrale): void {
    this.editForm.patchValue({
      id: refcadastrale.id,
      codeSection: refcadastrale.codeSection,
      codeParcelle: refcadastrale.codeParcelle,
      nicad: refcadastrale.nicad,
      superfici: refcadastrale.superfici,
      titreMere: refcadastrale.titreMere,
      titreCree: refcadastrale.titreCree,
      numeroRequisition: refcadastrale.numeroRequisition,
      dateBornage: refcadastrale.dateBornage ? refcadastrale.dateBornage.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createFromForm(): IRefcadastrale {
    return {
      ...new Refcadastrale(),
      id: this.editForm.get(['id'])!.value,
      codeSection: this.editForm.get(['codeSection'])!.value,
      codeParcelle: this.editForm.get(['codeParcelle'])!.value,
      nicad: this.editForm.get(['nicad'])!.value,
      superfici: this.editForm.get(['superfici'])!.value,
      titreMere: this.editForm.get(['titreMere'])!.value,
      titreCree: this.editForm.get(['titreCree'])!.value,
      numeroRequisition: this.editForm.get(['numeroRequisition'])!.value,
      dateBornage: this.editForm.get(['dateBornage'])!.value
        ? dayjs(this.editForm.get(['dateBornage'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
