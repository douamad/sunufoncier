import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRefcadastrale, Refcadastrale } from '../refcadastrale.model';
import { RefcadastraleService } from '../service/refcadastrale.service';

@Injectable({ providedIn: 'root' })
export class RefcadastraleRoutingResolveService implements Resolve<IRefcadastrale> {
  constructor(protected service: RefcadastraleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRefcadastrale> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((refcadastrale: HttpResponse<Refcadastrale>) => {
          if (refcadastrale.body) {
            return of(refcadastrale.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Refcadastrale());
  }
}
