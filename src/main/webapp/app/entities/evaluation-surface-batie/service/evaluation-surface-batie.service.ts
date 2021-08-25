import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEvaluationSurfaceBatie, getEvaluationSurfaceBatieIdentifier } from '../evaluation-surface-batie.model';

export type EntityResponseType = HttpResponse<IEvaluationSurfaceBatie>;
export type EntityArrayResponseType = HttpResponse<IEvaluationSurfaceBatie[]>;

@Injectable({ providedIn: 'root' })
export class EvaluationSurfaceBatieService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/evaluation-surface-baties');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(evaluationSurfaceBatie: IEvaluationSurfaceBatie): Observable<EntityResponseType> {
    return this.http.post<IEvaluationSurfaceBatie>(this.resourceUrl, evaluationSurfaceBatie, { observe: 'response' });
  }

  update(evaluationSurfaceBatie: IEvaluationSurfaceBatie): Observable<EntityResponseType> {
    return this.http.put<IEvaluationSurfaceBatie>(
      `${this.resourceUrl}/${getEvaluationSurfaceBatieIdentifier(evaluationSurfaceBatie) as number}`,
      evaluationSurfaceBatie,
      { observe: 'response' }
    );
  }

  partialUpdate(evaluationSurfaceBatie: IEvaluationSurfaceBatie): Observable<EntityResponseType> {
    return this.http.patch<IEvaluationSurfaceBatie>(
      `${this.resourceUrl}/${getEvaluationSurfaceBatieIdentifier(evaluationSurfaceBatie) as number}`,
      evaluationSurfaceBatie,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvaluationSurfaceBatie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvaluationSurfaceBatie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByDossier(idDossier: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption();
    return this.http.get<IEvaluationSurfaceBatie[]>(`this.resourceUrl/bydossier/${idDossier}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEvaluationSurfaceBatieToCollectionIfMissing(
    evaluationSurfaceBatieCollection: IEvaluationSurfaceBatie[],
    ...evaluationSurfaceBatiesToCheck: (IEvaluationSurfaceBatie | null | undefined)[]
  ): IEvaluationSurfaceBatie[] {
    const evaluationSurfaceBaties: IEvaluationSurfaceBatie[] = evaluationSurfaceBatiesToCheck.filter(isPresent);
    if (evaluationSurfaceBaties.length > 0) {
      const evaluationSurfaceBatieCollectionIdentifiers = evaluationSurfaceBatieCollection.map(
        evaluationSurfaceBatieItem => getEvaluationSurfaceBatieIdentifier(evaluationSurfaceBatieItem)!
      );
      const evaluationSurfaceBatiesToAdd = evaluationSurfaceBaties.filter(evaluationSurfaceBatieItem => {
        const evaluationSurfaceBatieIdentifier = getEvaluationSurfaceBatieIdentifier(evaluationSurfaceBatieItem);
        if (
          evaluationSurfaceBatieIdentifier == null ||
          evaluationSurfaceBatieCollectionIdentifiers.includes(evaluationSurfaceBatieIdentifier)
        ) {
          return false;
        }
        evaluationSurfaceBatieCollectionIdentifiers.push(evaluationSurfaceBatieIdentifier);
        return true;
      });
      return [...evaluationSurfaceBatiesToAdd, ...evaluationSurfaceBatieCollection];
    }
    return evaluationSurfaceBatieCollection;
  }
}
