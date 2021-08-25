import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEvaluationBatiments, EvaluationBatiments } from '../evaluation-batiments.model';
import { EvaluationBatimentsService } from '../service/evaluation-batiments.service';

@Injectable({ providedIn: 'root' })
export class EvaluationBatimentsRoutingResolveService implements Resolve<IEvaluationBatiments> {
  constructor(protected service: EvaluationBatimentsService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvaluationBatiments> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((evaluationBatiments: HttpResponse<EvaluationBatiments>) => {
          if (evaluationBatiments.body) {
            return of(evaluationBatiments.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EvaluationBatiments());
  }
}
