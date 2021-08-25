import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefParcelaire } from '../ref-parcelaire.model';

@Component({
  selector: 'jhi-ref-parcelaire-detail',
  templateUrl: './ref-parcelaire-detail.component.html',
})
export class RefParcelaireDetailComponent implements OnInit {
  refParcelaire: IRefParcelaire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refParcelaire }) => {
      this.refParcelaire = refParcelaire;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
