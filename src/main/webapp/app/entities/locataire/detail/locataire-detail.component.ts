import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocataire } from '../locataire.model';

@Component({
  selector: 'jhi-locataire-detail',
  templateUrl: './locataire-detail.component.html',
})
export class LocataireDetailComponent implements OnInit {
  locataire: ILocataire | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ locataire }) => {
      this.locataire = locataire;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
