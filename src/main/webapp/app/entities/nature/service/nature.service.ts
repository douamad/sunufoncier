import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { INature, getNatureIdentifier } from '../nature.model';

export type EntityResponseType = HttpResponse<INature>;
export type EntityArrayResponseType = HttpResponse<INature[]>;

@Injectable({ providedIn: 'root' })
export class NatureService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/natures');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(nature: INature): Observable<EntityResponseType> {
    return this.http.post<INature>(this.resourceUrl, nature, { observe: 'response' });
  }

  update(nature: INature): Observable<EntityResponseType> {
    return this.http.put<INature>(`${this.resourceUrl}/${getNatureIdentifier(nature) as number}`, nature, { observe: 'response' });
  }

  partialUpdate(nature: INature): Observable<EntityResponseType> {
    return this.http.patch<INature>(`${this.resourceUrl}/${getNatureIdentifier(nature) as number}`, nature, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INature>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INature[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addNatureToCollectionIfMissing(natureCollection: INature[], ...naturesToCheck: (INature | null | undefined)[]): INature[] {
    const natures: INature[] = naturesToCheck.filter(isPresent);
    if (natures.length > 0) {
      const natureCollectionIdentifiers = natureCollection.map(natureItem => getNatureIdentifier(natureItem)!);
      const naturesToAdd = natures.filter(natureItem => {
        const natureIdentifier = getNatureIdentifier(natureItem);
        if (natureIdentifier == null || natureCollectionIdentifiers.includes(natureIdentifier)) {
          return false;
        }
        natureCollectionIdentifiers.push(natureIdentifier);
        return true;
      });
      return [...naturesToAdd, ...natureCollection];
    }
    return natureCollection;
  }
}
