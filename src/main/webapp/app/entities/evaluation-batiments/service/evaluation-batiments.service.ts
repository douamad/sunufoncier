import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEvaluationBatiments, getEvaluationBatimentsIdentifier } from '../evaluation-batiments.model';

export type EntityResponseType = HttpResponse<IEvaluationBatiments>;
export type EntityArrayResponseType = HttpResponse<IEvaluationBatiments[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationBatimentsService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/evaluation-batiments');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(evaluationBatiments: IEvaluationBatiments): Observable<EntityResponseType> {
    return this.http.post<IEvaluationBatiments>(this.resourceUrl, evaluationBatiments, { observe: 'response' });
  }

  update(evaluationBatiments: IEvaluationBatiments): Observable<EntityResponseType> {
    return this.http.put<IEvaluationBatiments>(
      `${this.resourceUrl}/${getEvaluationBatimentsIdentifier(evaluationBatiments) as number}`,
      evaluationBatiments,
      { observe: 'response' }
    );
  }

  partialUpdate(evaluationBatiments: IEvaluationBatiments): Observable<EntityResponseType> {
    return this.http.patch<IEvaluationBatiments>(
      `${this.resourceUrl}/${getEvaluationBatimentsIdentifier(evaluationBatiments) as number}`,
      evaluationBatiments,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvaluationBatiments>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluationBatiments[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluationBatiments[]>(`${this.resourceUrl}/list`, { params: options, observe: 'response' });
  }

  queryByDossier(idDossier: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption();
    return this.http.get<IEvaluationBatiments[]>(`this.resourceUrl/bydossier/${idDossier}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEvaluationBatimentsToCollectionIfMissing(
    evaluationBatimentsCollection: IEvaluationBatiments[],
    ...evaluationBatimentsToCheck: (IEvaluationBatiments | null | undefined)[]
  ): IEvaluationBatiments[] {
    const evaluationBatiments: IEvaluationBatiments[] = evaluationBatimentsToCheck.filter(isPresent);
    if (evaluationBatiments.length > 0) {
      const evaluationBatimentsCollectionIdentifiers = evaluationBatimentsCollection.map(
        evaluationBatimentsItem => getEvaluationBatimentsIdentifier(evaluationBatimentsItem)!
      );
      const evaluationBatimentsToAdd = evaluationBatiments.filter(evaluationBatimentsItem => {
        const evaluationBatimentsIdentifier = getEvaluationBatimentsIdentifier(evaluationBatimentsItem);
        if (evaluationBatimentsIdentifier == null || evaluationBatimentsCollectionIdentifiers.includes(evaluationBatimentsIdentifier)) {
          return false;
        }
        evaluationBatimentsCollectionIdentifiers.push(evaluationBatimentsIdentifier);
        return true;
      });
      return [...evaluationBatimentsToAdd, ...evaluationBatimentsCollection];
    }
    return evaluationBatimentsCollection;
  }
}
