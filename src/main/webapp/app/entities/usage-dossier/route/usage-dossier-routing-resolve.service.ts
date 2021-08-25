import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUsageDossier, UsageDossier } from '../usage-dossier.model';
import { UsageDossierService } from '../service/usage-dossier.service';

@Injectable({ providedIn: 'root' })
export class UsageDossierRoutingResolveService implements Resolve<IUsageDossier> {
  constructor(protected service: UsageDossierService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUsageDossier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((usageDossier: HttpResponse<UsageDossier>) => {
          if (usageDossier.body) {
            return of(usageDossier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UsageDossier());
  }
}
