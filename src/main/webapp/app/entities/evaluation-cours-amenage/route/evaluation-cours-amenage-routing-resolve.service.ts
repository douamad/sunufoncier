import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEvaluationCoursAmenage, EvaluationCoursAmenage } from '../evaluation-cours-amenage.model';
import { EvaluationCoursAmenageService } from '../service/evaluation-cours-amenage.service';

@Injectable({ providedIn: 'root' })
export class EvaluationCoursAmenageRoutingResolveService implements Resolve<IEvaluationCoursAmenage> {
  constructor(protected service: EvaluationCoursAmenageService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEvaluationCoursAmenage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((evaluationCoursAmenage: HttpResponse<EvaluationCoursAmenage>) => {
          if (evaluationCoursAmenage.body) {
            return of(evaluationCoursAmenage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EvaluationCoursAmenage());
  }
}
