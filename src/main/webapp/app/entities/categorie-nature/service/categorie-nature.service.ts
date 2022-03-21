import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorieNature, getCategorieNatureIdentifier } from '../categorie-nature.model';

export type EntityResponseType = HttpResponse<ICategorieNature>;
export type EntityArrayResponseType = HttpResponse<ICategorieNature[]>;

@Injectable({ providedIn: 'root' })
export class CategorieNatureService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-natures');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(categorieNature: ICategorieNature): Observable<EntityResponseType> {
    return this.http.post<ICategorieNature>(this.resourceUrl, categorieNature, { observe: 'response' });
  }

  update(categorieNature: ICategorieNature): Observable<EntityResponseType> {
    return this.http.put<ICategorieNature>(
      `${this.resourceUrl}/${getCategorieNatureIdentifier(categorieNature) as number}`,
      categorieNature,
      { observe: 'response' }
    );
  }

  partialUpdate(categorieNature: ICategorieNature): Observable<EntityResponseType> {
    return this.http.patch<ICategorieNature>(
      `${this.resourceUrl}/${getCategorieNatureIdentifier(categorieNature) as number}`,
      categorieNature,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieNature>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieNature[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieNature[]>(`${this.resourceUrl}/list`, { params: options, observe: 'response' });
  }
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCategorieNatureToCollectionIfMissing(
    categorieNatureCollection: ICategorieNature[],
    ...categorieNaturesToCheck: (ICategorieNature | null | undefined)[]
  ): ICategorieNature[] {
    const categorieNatures: ICategorieNature[] = categorieNaturesToCheck.filter(isPresent);
    if (categorieNatures.length > 0) {
      const categorieNatureCollectionIdentifiers = categorieNatureCollection.map(
        categorieNatureItem => getCategorieNatureIdentifier(categorieNatureItem)!
      );
      const categorieNaturesToAdd = categorieNatures.filter(categorieNatureItem => {
        const categorieNatureIdentifier = getCategorieNatureIdentifier(categorieNatureItem);
        if (categorieNatureIdentifier == null || categorieNatureCollectionIdentifiers.includes(categorieNatureIdentifier)) {
          return false;
        }
        categorieNatureCollectionIdentifiers.push(categorieNatureIdentifier);
        return true;
      });
      return [...categorieNaturesToAdd, ...categorieNatureCollection];
    }
    return categorieNatureCollection;
  }
}
