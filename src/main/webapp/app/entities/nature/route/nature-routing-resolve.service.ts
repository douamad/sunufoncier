import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INature, Nature } from '../nature.model';
import { NatureService } from '../service/nature.service';

@Injectable({ providedIn: 'root' })
export class NatureRoutingResolveService implements Resolve<INature> {
  constructor(protected service: NatureService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INature> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nature: HttpResponse<Nature>) => {
          if (nature.body) {
            return of(nature.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Nature());
  }
}
