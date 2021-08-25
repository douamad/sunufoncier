import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILocataire, getLocataireIdentifier } from '../locataire.model';
import { IEvaluationSurfaceBatie } from 'app/entities/evaluation-surface-batie/evaluation-surface-batie.model';

export type EntityResponseType = HttpResponse<ILocataire>;
export type EntityArrayResponseType = HttpResponse<ILocataire[]>;

@Injectable({ providedIn: 'root' })
export class LocataireService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/locataires');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(locataire: ILocataire): Observable<EntityResponseType> {
    return this.http.post<ILocataire>(this.resourceUrl, locataire, { observe: 'response' });
  }

  update(locataire: ILocataire): Observable<EntityResponseType> {
    return this.http.put<ILocataire>(`${this.resourceUrl}/${getLocataireIdentifier(locataire) as number}`, locataire, {
      observe: 'response',
    });
  }

  partialUpdate(locataire: ILocataire): Observable<EntityResponseType> {
    return this.http.patch<ILocataire>(`${this.resourceUrl}/${getLocataireIdentifier(locataire) as number}`, locataire, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILocataire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILocataire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryByDossier(idDossier: number): Observable<EntityArrayResponseType> {
    const options = createRequestOption();
    return this.http.get<ILocataire[]>(`this.resourceUrl/bydossier/${idDossier}`, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLocataireToCollectionIfMissing(
    locataireCollection: ILocataire[],
    ...locatairesToCheck: (ILocataire | null | undefined)[]
  ): ILocataire[] {
    const locataires: ILocataire[] = locatairesToCheck.filter(isPresent);
    if (locataires.length > 0) {
      const locataireCollectionIdentifiers = locataireCollection.map(locataireItem => getLocataireIdentifier(locataireItem)!);
      const locatairesToAdd = locataires.filter(locataireItem => {
        const locataireIdentifier = getLocataireIdentifier(locataireItem);
        if (locataireIdentifier == null || locataireCollectionIdentifiers.includes(locataireIdentifier)) {
          return false;
        }
        locataireCollectionIdentifiers.push(locataireIdentifier);
        return true;
      });
      return [...locatairesToAdd, ...locataireCollection];
    }
    return locataireCollection;
  }
}
