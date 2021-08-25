import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUsageDossier, getUsageDossierIdentifier } from '../usage-dossier.model';

export type EntityResponseType = HttpResponse<IUsageDossier>;
export type EntityArrayResponseType = HttpResponse<IUsageDossier[]>;

@Injectable({ providedIn: 'root' })
export class UsageDossierService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/usage-dossiers');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(usageDossier: IUsageDossier): Observable<EntityResponseType> {
    return this.http.post<IUsageDossier>(this.resourceUrl, usageDossier, { observe: 'response' });
  }

  update(usageDossier: IUsageDossier): Observable<EntityResponseType> {
    return this.http.put<IUsageDossier>(`${this.resourceUrl}/${getUsageDossierIdentifier(usageDossier) as number}`, usageDossier, {
      observe: 'response',
    });
  }

  partialUpdate(usageDossier: IUsageDossier): Observable<EntityResponseType> {
    return this.http.patch<IUsageDossier>(`${this.resourceUrl}/${getUsageDossierIdentifier(usageDossier) as number}`, usageDossier, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUsageDossier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUsageDossier[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addUsageDossierToCollectionIfMissing(
    usageDossierCollection: IUsageDossier[],
    ...usageDossiersToCheck: (IUsageDossier | null | undefined)[]
  ): IUsageDossier[] {
    const usageDossiers: IUsageDossier[] = usageDossiersToCheck.filter(isPresent);
    if (usageDossiers.length > 0) {
      const usageDossierCollectionIdentifiers = usageDossierCollection.map(
        usageDossierItem => getUsageDossierIdentifier(usageDossierItem)!
      );
      const usageDossiersToAdd = usageDossiers.filter(usageDossierItem => {
        const usageDossierIdentifier = getUsageDossierIdentifier(usageDossierItem);
        if (usageDossierIdentifier == null || usageDossierCollectionIdentifiers.includes(usageDossierIdentifier)) {
          return false;
        }
        usageDossierCollectionIdentifiers.push(usageDossierIdentifier);
        return true;
      });
      return [...usageDossiersToAdd, ...usageDossierCollection];
    }
    return usageDossierCollection;
  }
}
