import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEvaluationCloture, EvaluationCloture } from '../evaluation-cloture.model';
import { EvaluationClotureService } from '../service/evaluation-cloture.service';

@Injectable({ providedIn: 'root' })
export class EvaluationClotureRoutingResolveService implements Resolve<IEvaluationCloture> {
  constructor(protected service: EvaluationClotureService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvaluationCloture> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((evaluationCloture: HttpResponse<EvaluationCloture>) => {
          if (evaluationCloture.body) {
            return of(evaluationCloture.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EvaluationCloture());
  }
}
