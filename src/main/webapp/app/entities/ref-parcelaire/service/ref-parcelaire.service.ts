import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRefParcelaire, getRefParcelaireIdentifier } from '../ref-parcelaire.model';

export type EntityResponseType = HttpResponse<IRefParcelaire>;
export type EntityArrayResponseType = HttpResponse<IRefParcelaire[]>;

@Injectable({ providedIn: 'root' })
export class RefParcelaireService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/ref-parcelaires');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(refParcelaire: IRefParcelaire): Observable<EntityResponseType> {
    return this.http.post<IRefParcelaire>(this.resourceUrl, refParcelaire, { observe: 'response' });
  }

  update(refParcelaire: IRefParcelaire): Observable<EntityResponseType> {
    return this.http.put<IRefParcelaire>(`${this.resourceUrl}/${getRefParcelaireIdentifier(refParcelaire) as number}`, refParcelaire, {
      observe: 'response',
    });
  }

  partialUpdate(refParcelaire: IRefParcelaire): Observable<EntityResponseType> {
    return this.http.patch<IRefParcelaire>(`${this.resourceUrl}/${getRefParcelaireIdentifier(refParcelaire) as number}`, refParcelaire, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRefParcelaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRefParcelaire[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRefParcelaireToCollectionIfMissing(
    refParcelaireCollection: IRefParcelaire[],
    ...refParcelairesToCheck: (IRefParcelaire | null | undefined)[]
  ): IRefParcelaire[] {
    const refParcelaires: IRefParcelaire[] = refParcelairesToCheck.filter(isPresent);
    if (refParcelaires.length > 0) {
      const refParcelaireCollectionIdentifiers = refParcelaireCollection.map(
        refParcelaireItem => getRefParcelaireIdentifier(refParcelaireItem)!
      );
      const refParcelairesToAdd = refParcelaires.filter(refParcelaireItem => {
        const refParcelaireIdentifier = getRefParcelaireIdentifier(refParcelaireItem);
        if (refParcelaireIdentifier == null || refParcelaireCollectionIdentifiers.includes(refParcelaireIdentifier)) {
          return false;
        }
        refParcelaireCollectionIdentifiers.push(refParcelaireIdentifier);
        return true;
      });
      return [...refParcelairesToAdd, ...refParcelaireCollection];
    }
    return refParcelaireCollection;
  }
}
