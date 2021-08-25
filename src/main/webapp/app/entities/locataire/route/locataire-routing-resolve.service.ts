import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ILocataire, Locataire } from '../locataire.model';
import { LocataireService } from '../service/locataire.service';

@Injectable({ providedIn: 'root' })
export class LocataireRoutingResolveService implements Resolve<ILocataire> {
  constructor(protected service: LocataireService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILocataire> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((locataire: HttpResponse<Locataire>) => {
          if (locataire.body) {
            return of(locataire.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Locataire());
  }
}
