import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRefcadastrale } from '../refcadastrale.model';
import { RefcadastraleService } from '../service/refcadastrale.service';

@Component({
  templateUrl: './refcadastrale-delete-dialog.component.html',
})
export class RefcadastraleDeleteDialogComponent {
  refcadastrale?: IRefcadastrale;

  constructor(protected refcadastraleService: RefcadastraleService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.refcadastraleService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
