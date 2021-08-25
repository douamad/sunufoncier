import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorieCoursAmenage, CategorieCoursAmenage } from '../categorie-cours-amenage.model';
import { CategorieCoursAmenageService } from '../service/categorie-cours-amenage.service';

@Injectable({ providedIn: 'root' })
export class CategorieCoursAmenageRoutingResolveService implements Resolve<ICategorieCoursAmenage> {
  constructor(protected service: CategorieCoursAmenageService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategorieCoursAmenage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((categorieCoursAmenage: HttpResponse<CategorieCoursAmenage>) => {
          if (categorieCoursAmenage.body) {
            return of(categorieCoursAmenage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategorieCoursAmenage());
  }
}
