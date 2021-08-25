import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorieBatie, getCategorieBatieIdentifier } from '../categorie-batie.model';

export type EntityResponseType = HttpResponse<ICategorieBatie>;
export type EntityArrayResponseType = HttpResponse<ICategorieBatie[]>;

@Injectable({ providedIn: 'root' })
export class CategorieBatieService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-baties');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(categorieBatie: ICategorieBatie): Observable<EntityResponseType> {
    return this.http.post<ICategorieBatie>(this.resourceUrl, categorieBatie, { observe: 'response' });
  }

  update(categorieBatie: ICategorieBatie): Observable<EntityResponseType> {
    return this.http.put<ICategorieBatie>(`${this.resourceUrl}/${getCategorieBatieIdentifier(categorieBatie) as number}`, categorieBatie, {
      observe: 'response',
    });
  }

  partialUpdate(categorieBatie: ICategorieBatie): Observable<EntityResponseType> {
    return this.http.patch<ICategorieBatie>(
      `${this.resourceUrl}/${getCategorieBatieIdentifier(categorieBatie) as number}`,
      categorieBatie,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieBatie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieBatie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCategorieBatieToCollectionIfMissing(
    categorieBatieCollection: ICategorieBatie[],
    ...categorieBatiesToCheck: (ICategorieBatie | null | undefined)[]
  ): ICategorieBatie[] {
    const categorieBaties: ICategorieBatie[] = categorieBatiesToCheck.filter(isPresent);
    if (categorieBaties.length > 0) {
      const categorieBatieCollectionIdentifiers = categorieBatieCollection.map(
        categorieBatieItem => getCategorieBatieIdentifier(categorieBatieItem)!
      );
      const categorieBatiesToAdd = categorieBaties.filter(categorieBatieItem => {
        const categorieBatieIdentifier = getCategorieBatieIdentifier(categorieBatieItem);
        if (categorieBatieIdentifier == null || categorieBatieCollectionIdentifiers.includes(categorieBatieIdentifier)) {
          return false;
        }
        categorieBatieCollectionIdentifiers.push(categorieBatieIdentifier);
        return true;
      });
      return [...categorieBatiesToAdd, ...categorieBatieCollection];
    }
    return categorieBatieCollection;
  }
}
