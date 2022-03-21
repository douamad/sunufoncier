import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEvaluationCloture, getEvaluationClotureIdentifier } from '../evaluation-cloture.model';

export type EntityResponseType = HttpResponse<IEvaluationCloture>;
export type EntityArrayResponseType = HttpResponse<IEvaluationCloture[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationClotureService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/evaluation-clotures');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(evaluationCloture: IEvaluationCloture): Observable<EntityResponseType> {
    return this.http.post<IEvaluationCloture>(this.resourceUrl, evaluationCloture, { observe: 'response' });
  }

  update(evaluationCloture: IEvaluationCloture): Observable<EntityResponseType> {
    return this.http.put<IEvaluationCloture>(
      `${this.resourceUrl}/${getEvaluationClotureIdentifier(evaluationCloture) as number}`,
      evaluationCloture,
      { observe: 'response' }
    );
  }

  partialUpdate(evaluationCloture: IEvaluationCloture): Observable<EntityResponseType> {
    return this.http.patch<IEvaluationCloture>(
      `${this.resourceUrl}/${getEvaluationClotureIdentifier(evaluationCloture) as number}`,
      evaluationCloture,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvaluationCloture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluationCloture[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluationCloture[]>(`${this.resourceUrl}/list`, { params: options, observe: 'response' });
  }

  queryByDossier(idDossier: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption();
    return this.http.get<IEvaluationCloture[]>(`this.resourceUrl/bydossier/${idDossier}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEvaluationClotureToCollectionIfMissing(
    evaluationClotureCollection: IEvaluationCloture[],
    ...evaluationCloturesToCheck: (IEvaluationCloture | null | undefined)[]
  ): IEvaluationCloture[] {
    const evaluationClotures: IEvaluationCloture[] = evaluationCloturesToCheck.filter(isPresent);
    if (evaluationClotures.length > 0) {
      const evaluationClotureCollectionIdentifiers = evaluationClotureCollection.map(
        evaluationClotureItem => getEvaluationClotureIdentifier(evaluationClotureItem)!
      );
      const evaluationCloturesToAdd = evaluationClotures.filter(evaluationClotureItem => {
        const evaluationClotureIdentifier = getEvaluationClotureIdentifier(evaluationClotureItem);
        if (evaluationClotureIdentifier == null || evaluationClotureCollectionIdentifiers.includes(evaluationClotureIdentifier)) {
          return false;
        }
        evaluationClotureCollectionIdentifiers.push(evaluationClotureIdentifier);
        return true;
      });
      return [...evaluationCloturesToAdd, ...evaluationClotureCollection];
    }
    return evaluationClotureCollection;
  }
}
