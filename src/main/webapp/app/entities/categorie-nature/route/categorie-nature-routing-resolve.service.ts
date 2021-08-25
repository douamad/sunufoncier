import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorieNature, CategorieNature } from '../categorie-nature.model';
import { CategorieNatureService } from '../service/categorie-nature.service';

@Injectable({ providedIn: 'root' })
export class CategorieNatureRoutingResolveService implements Resolve<ICategorieNature> {
  constructor(protected service: CategorieNatureService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieNature> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categorieNature: HttpResponse<CategorieNature>) => {
          if (categorieNature.body) {
            return of(categorieNature.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategorieNature());
  }
}
