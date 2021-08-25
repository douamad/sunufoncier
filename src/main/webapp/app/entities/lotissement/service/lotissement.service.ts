import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ILotissement, getLotissementIdentifier } from '../lotissement.model';

export type EntityResponseType = HttpResponse<ILotissement>;
export type EntityArrayResponseType = HttpResponse<ILotissement[]>;

@Injectable({ providedIn: 'root' })
export class LotissementService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/lotissements');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(lotissement: ILotissement): Observable<EntityResponseType> {
    return this.http.post<ILotissement>(this.resourceUrl, lotissement, { observe: 'response' });
  }

  update(lotissement: ILotissement): Observable<EntityResponseType> {
    return this.http.put<ILotissement>(`${this.resourceUrl}/${getLotissementIdentifier(lotissement) as number}`, lotissement, {
      observe: 'response',
    });
  }

  partialUpdate(lotissement: ILotissement): Observable<EntityResponseType> {
    return this.http.patch<ILotissement>(`${this.resourceUrl}/${getLotissementIdentifier(lotissement) as number}`, lotissement, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILotissement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILotissement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addLotissementToCollectionIfMissing(
    lotissementCollection: ILotissement[],
    ...lotissementsToCheck: (ILotissement | null | undefined)[]
  ): ILotissement[] {
    const lotissements: ILotissement[] = lotissementsToCheck.filter(isPresent);
    if (lotissements.length > 0) {
      const lotissementCollectionIdentifiers = lotissementCollection.map(lotissementItem => getLotissementIdentifier(lotissementItem)!);
      const lotissementsToAdd = lotissements.filter(lotissementItem => {
        const lotissementIdentifier = getLotissementIdentifier(lotissementItem);
        if (lotissementIdentifier == null || lotissementCollectionIdentifiers.includes(lotissementIdentifier)) {
          return false;
        }
        lotissementCollectionIdentifiers.push(lotissementIdentifier);
        return true;
      });
      return [...lotissementsToAdd, ...lotissementCollection];
    }
    return lotissementCollection;
  }
}
