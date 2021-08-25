import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILotissement, Lotissement } from '../lotissement.model';
import { LotissementService } from '../service/lotissement.service';

@Injectable({ providedIn: 'root' })
export class LotissementRoutingResolveService implements Resolve<ILotissement> {
  constructor(protected service: LotissementService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILotissement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((lotissement: HttpResponse<Lotissement>) => {
          if (lotissement.body) {
            return of(lotissement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Lotissement());
  }
}
