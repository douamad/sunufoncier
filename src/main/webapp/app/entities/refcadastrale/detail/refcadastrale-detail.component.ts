import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefcadastrale } from '../refcadastrale.model';

@Component({
  selector: 'jhi-refcadastrale-detail',
  templateUrl: './refcadastrale-detail.component.html',
})
export class RefcadastraleDetailComponent implements OnInit {
  refcadastrale: IRefcadastrale | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ refcadastrale }) => {
      this.refcadastrale = refcadastrale;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
