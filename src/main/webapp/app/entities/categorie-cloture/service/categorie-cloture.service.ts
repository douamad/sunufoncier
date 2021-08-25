import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorieCloture, getCategorieClotureIdentifier } from '../categorie-cloture.model';

export type EntityResponseType = HttpResponse<ICategorieCloture>;
export type EntityArrayResponseType = HttpResponse<ICategorieCloture[]>;

@Injectable({ providedIn: 'root' })
export class CategorieClotureService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-clotures');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(categorieCloture: ICategorieCloture): Observable<EntityResponseType> {
    return this.http.post<ICategorieCloture>(this.resourceUrl, categorieCloture, { observe: 'response' });
  }

  update(categorieCloture: ICategorieCloture): Observable<EntityResponseType> {
    return this.http.put<ICategorieCloture>(
      `${this.resourceUrl}/${getCategorieClotureIdentifier(categorieCloture) as number}`,
      categorieCloture,
      { observe: 'response' }
    );
  }

  partialUpdate(categorieCloture: ICategorieCloture): Observable<EntityResponseType> {
    return this.http.patch<ICategorieCloture>(
      `${this.resourceUrl}/${getCategorieClotureIdentifier(categorieCloture) as number}`,
      categorieCloture,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieCloture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieCloture[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCategorieClotureToCollectionIfMissing(
    categorieClotureCollection: ICategorieCloture[],
    ...categorieCloturesToCheck: (ICategorieCloture | null | undefined)[]
  ): ICategorieCloture[] {
    const categorieClotures: ICategorieCloture[] = categorieCloturesToCheck.filter(isPresent);
    if (categorieClotures.length > 0) {
      const categorieClotureCollectionIdentifiers = categorieClotureCollection.map(
        categorieClotureItem => getCategorieClotureIdentifier(categorieClotureItem)!
      );
      const categorieCloturesToAdd = categorieClotures.filter(categorieClotureItem => {
        const categorieClotureIdentifier = getCategorieClotureIdentifier(categorieClotureItem);
        if (categorieClotureIdentifier == null || categorieClotureCollectionIdentifiers.includes(categorieClotureIdentifier)) {
          return false;
        }
        categorieClotureCollectionIdentifiers.push(categorieClotureIdentifier);
        return true;
      });
      return [...categorieCloturesToAdd, ...categorieClotureCollection];
    }
    return categorieClotureCollection;
  }
}
