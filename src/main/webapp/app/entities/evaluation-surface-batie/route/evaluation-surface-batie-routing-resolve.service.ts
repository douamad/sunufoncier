import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEvaluationSurfaceBatie, EvaluationSurfaceBatie } from '../evaluation-surface-batie.model';
import { EvaluationSurfaceBatieService } from '../service/evaluation-surface-batie.service';

@Injectable({ providedIn: 'root' })
export class EvaluationSurfaceBatieRoutingResolveService implements Resolve<IEvaluationSurfaceBatie> {
  constructor(protected service: EvaluationSurfaceBatieService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvaluationSurfaceBatie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((evaluationSurfaceBatie: HttpResponse<EvaluationSurfaceBatie>) => {
          if (evaluationSurfaceBatie.body) {
            return of(evaluationSurfaceBatie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EvaluationSurfaceBatie());
  }
}
