import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorieCloture, CategorieCloture } from '../categorie-cloture.model';
import { CategorieClotureService } from '../service/categorie-cloture.service';

@Injectable({ providedIn: 'root' })
export class CategorieClotureRoutingResolveService implements Resolve<ICategorieCloture> {
  constructor(protected service: CategorieClotureService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieCloture> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categorieCloture: HttpResponse<CategorieCloture>) => {
          if (categorieCloture.body) {
            return of(categorieCloture.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategorieCloture());
  }
}
