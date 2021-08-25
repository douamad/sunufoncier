import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRepresentant, getRepresentantIdentifier } from '../representant.model';

export type EntityResponseType = HttpResponse<IRepresentant>;
export type EntityArrayResponseType = HttpResponse<IRepresentant[]>;

@Injectable({ providedIn: 'root' })
export class RepresentantService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/representants');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(representant: IRepresentant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(representant);
    return this.http
      .post<IRepresentant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(representant: IRepresentant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(representant);
    return this.http
      .put<IRepresentant>(`${this.resourceUrl}/${getRepresentantIdentifier(representant) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(representant: IRepresentant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(representant);
    return this.http
      .patch<IRepresentant>(`${this.resourceUrl}/${getRepresentantIdentifier(representant) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRepresentant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRepresentant[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRepresentantToCollectionIfMissing(
    representantCollection: IRepresentant[],
    ...representantsToCheck: (IRepresentant | null | undefined)[]
  ): IRepresentant[] {
    const representants: IRepresentant[] = representantsToCheck.filter(isPresent);
    if (representants.length > 0) {
      const representantCollectionIdentifiers = representantCollection.map(
        representantItem => getRepresentantIdentifier(representantItem)!
      );
      const representantsToAdd = representants.filter(representantItem => {
        const representantIdentifier = getRepresentantIdentifier(representantItem);
        if (representantIdentifier == null || representantCollectionIdentifiers.includes(representantIdentifier)) {
          return false;
        }
        representantCollectionIdentifiers.push(representantIdentifier);
        return true;
      });
      return [...representantsToAdd, ...representantCollection];
    }
    return representantCollection;
  }

  protected convertDateFromClient(representant: IRepresentant): IRepresentant {
    return Object.assign({}, representant, {
      dateNaiss: representant.dateNaiss?.isValid() ? representant.dateNaiss.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaiss = res.body.dateNaiss ? dayjs(res.body.dateNaiss) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((representant: IRepresentant) => {
        representant.dateNaiss = representant.dateNaiss ? dayjs(representant.dateNaiss) : undefined;
      });
    }
    return res;
  }
}
