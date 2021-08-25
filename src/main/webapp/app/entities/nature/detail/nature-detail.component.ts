import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INature } from '../nature.model';

@Component({
  selector: 'jhi-nature-detail',
  templateUrl: './nature-detail.component.html',
})
export class NatureDetailComponent implements OnInit {
  nature: INature | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nature }) => {
      this.nature = nature;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
