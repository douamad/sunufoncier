import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEvaluationCoursAmenage, getEvaluationCoursAmenageIdentifier } from '../evaluation-cours-amenage.model';

export type EntityResponseType = HttpResponse<IEvaluationCoursAmenage>;
export type EntityArrayResponseType = HttpResponse<IEvaluationCoursAmenage[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationCoursAmenageService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/evaluation-cours-amenages');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(evaluationCoursAmenage: IEvaluationCoursAmenage): Observable<EntityResponseType> {
    return this.http.post<IEvaluationCoursAmenage>(this.resourceUrl, evaluationCoursAmenage, { observe: 'response' });
  }

  update(evaluationCoursAmenage: IEvaluationCoursAmenage): Observable<EntityResponseType> {
    return this.http.put<IEvaluationCoursAmenage>(
      `${this.resourceUrl}/${getEvaluationCoursAmenageIdentifier(evaluationCoursAmenage) as number}`,
      evaluationCoursAmenage,
      { observe: 'response' }
    );
  }

  partialUpdate(evaluationCoursAmenage: IEvaluationCoursAmenage): Observable<EntityResponseType> {
    return this.http.patch<IEvaluationCoursAmenage>(
      `${this.resourceUrl}/${getEvaluationCoursAmenageIdentifier(evaluationCoursAmenage) as number}`,
      evaluationCoursAmenage,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvaluationCoursAmenage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluationCoursAmenage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryByDossier(idDossier: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption();
    return this.http.get<IEvaluationCoursAmenage[]>(`this.resourceUrl/bydossier/${idDossier}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEvaluationCoursAmenageToCollectionIfMissing(
    evaluationCoursAmenageCollection: IEvaluationCoursAmenage[],
    ...evaluationCoursAmenagesToCheck: (IEvaluationCoursAmenage | null | undefined)[]
  ): IEvaluationCoursAmenage[] {
    const evaluationCoursAmenages: IEvaluationCoursAmenage[] = evaluationCoursAmenagesToCheck.filter(isPresent);
    if (evaluationCoursAmenages.length > 0) {
      const evaluationCoursAmenageCollectionIdentifiers = evaluationCoursAmenageCollection.map(
        evaluationCoursAmenageItem => getEvaluationCoursAmenageIdentifier(evaluationCoursAmenageItem)!
      );
      const evaluationCoursAmenagesToAdd = evaluationCoursAmenages.filter(evaluationCoursAmenageItem => {
        const evaluationCoursAmenageIdentifier = getEvaluationCoursAmenageIdentifier(evaluationCoursAmenageItem);
        if (
          evaluationCoursAmenageIdentifier == null ||
          evaluationCoursAmenageCollectionIdentifiers.includes(evaluationCoursAmenageIdentifier)
        ) {
          return false;
        }
        evaluationCoursAmenageCollectionIdentifiers.push(evaluationCoursAmenageIdentifier);
        return true;
      });
      return [...evaluationCoursAmenagesToAdd, ...evaluationCoursAmenageCollection];
    }
    return evaluationCoursAmenageCollection;
  }
}
