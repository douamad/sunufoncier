import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRefParcelaire, RefParcelaire } from '../ref-parcelaire.model';
import { RefParcelaireService } from '../service/ref-parcelaire.service';

@Injectable({ providedIn: 'root' })
export class RefParcelaireRoutingResolveService implements Resolve<IRefParcelaire> {
  constructor(protected service: RefParcelaireService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRefParcelaire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((refParcelaire: HttpResponse<RefParcelaire>) => {
          if (refParcelaire.body) {
            return of(refParcelaire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RefParcelaire());
  }
}
