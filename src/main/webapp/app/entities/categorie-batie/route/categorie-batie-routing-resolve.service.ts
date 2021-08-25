import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorieBatie, CategorieBatie } from '../categorie-batie.model';
import { CategorieBatieService } from '../service/categorie-batie.service';

@Injectable({ providedIn: 'root' })
export class CategorieBatieRoutingResolveService implements Resolve<ICategorieBatie> {
  constructor(protected service: CategorieBatieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieBatie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categorieBatie: HttpResponse<CategorieBatie>) => {
          if (categorieBatie.body) {
            return of(categorieBatie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategorieBatie());
  }
}
