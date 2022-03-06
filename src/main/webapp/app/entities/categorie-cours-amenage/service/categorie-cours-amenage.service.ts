import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorieCoursAmenage, getCategorieCoursAmenageIdentifier } from '../categorie-cours-amenage.model';
import { ICategorieCloture } from 'app/entities/categorie-cloture/categorie-cloture.model';

export type EntityResponseType = HttpResponse<ICategorieCoursAmenage>;
export type EntityArrayResponseType = HttpResponse<ICategorieCoursAmenage[]>;

@Injectable({ providedIn: 'root' })
export class CategorieCoursAmenageService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/categorie-cours-amenages');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(categorieCoursAmenage: ICategorieCoursAmenage): Observable<EntityResponseType> {
    return this.http.post<ICategorieCoursAmenage>(this.resourceUrl, categorieCoursAmenage, { observe: 'response' });
  }

  update(categorieCoursAmenage: ICategorieCoursAmenage): Observable<EntityResponseType> {
    return this.http.put<ICategorieCoursAmenage>(
      `${this.resourceUrl}/${getCategorieCoursAmenageIdentifier(categorieCoursAmenage) as number}`,
      categorieCoursAmenage,
      { observe: 'response' }
    );
  }

  partialUpdate(categorieCoursAmenage: ICategorieCoursAmenage): Observable<EntityResponseType> {
    return this.http.patch<ICategorieCoursAmenage>(
      `${this.resourceUrl}/${getCategorieCoursAmenageIdentifier(categorieCoursAmenage) as number}`,
      categorieCoursAmenage,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieCoursAmenage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieCoursAmenage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
  queryAll(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieCoursAmenage[]>(`${this.resourceUrl}/list`, { params: options, observe: 'response' });
  }
  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCategorieCoursAmenageToCollectionIfMissing(
    categorieCoursAmenageCollection: ICategorieCoursAmenage[],
    ...categorieCoursAmenagesToCheck: (ICategorieCoursAmenage | null | undefined)[]
  ): ICategorieCoursAmenage[] {
    const categorieCoursAmenages: ICategorieCoursAmenage[] = categorieCoursAmenagesToCheck.filter(isPresent);
    if (categorieCoursAmenages.length > 0) {
      const categorieCoursAmenageCollectionIdentifiers = categorieCoursAmenageCollection.map(
        categorieCoursAmenageItem => getCategorieCoursAmenageIdentifier(categorieCoursAmenageItem)!
      );
      const categorieCoursAmenagesToAdd = categorieCoursAmenages.filter(categorieCoursAmenageItem => {
        const categorieCoursAmenageIdentifier = getCategorieCoursAmenageIdentifier(categorieCoursAmenageItem);
        if (
          categorieCoursAmenageIdentifier == null ||
          categorieCoursAmenageCollectionIdentifiers.includes(categorieCoursAmenageIdentifier)
        ) {
          return false;
        }
        categorieCoursAmenageCollectionIdentifiers.push(categorieCoursAmenageIdentifier);
        return true;
      });
      return [...categorieCoursAmenagesToAdd, ...categorieCoursAmenageCollection];
    }
    return categorieCoursAmenageCollection;
  }
}
