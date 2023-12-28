import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRefcadastrale, getRefcadastraleIdentifier } from '../refcadastrale.model';

export type EntityResponseType = HttpResponse<IRefcadastrale>;
export type EntityArrayResponseType = HttpResponse<IRefcadastrale[]>;

@Injectable({ providedIn: 'root' })
export class RefcadastraleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/refcadastrales');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(refcadastrale: IRefcadastrale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refcadastrale);
    return this.http
      .post<IRefcadastrale>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(refcadastrale: IRefcadastrale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refcadastrale);
    return this.http
      .put<IRefcadastrale>(`${this.resourceUrl}/${getRefcadastraleIdentifier(refcadastrale) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(refcadastrale: IRefcadastrale): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(refcadastrale);
    return this.http
      .patch<IRefcadastrale>(`${this.resourceUrl}/${getRefcadastraleIdentifier(refcadastrale) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IRefcadastrale>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IRefcadastrale[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRefcadastraleToCollectionIfMissing(
    refcadastraleCollection: IRefcadastrale[],
    ...refcadastralesToCheck: (IRefcadastrale | null | undefined)[]
  ): IRefcadastrale[] {
    const refcadastrales: IRefcadastrale[] = refcadastralesToCheck.filter(isPresent);
    if (refcadastrales.length > 0) {
      const refcadastraleCollectionIdentifiers = refcadastraleCollection.map(
        refcadastraleItem => getRefcadastraleIdentifier(refcadastraleItem)!
      );
      const refcadastralesToAdd = refcadastrales.filter(refcadastraleItem => {
        const refcadastraleIdentifier = getRefcadastraleIdentifier(refcadastraleItem);
        if (refcadastraleIdentifier == null || refcadastraleCollectionIdentifiers.includes(refcadastraleIdentifier)) {
          return false;
        }
        refcadastraleCollectionIdentifiers.push(refcadastraleIdentifier);
        return true;
      });
      return [...refcadastralesToAdd, ...refcadastraleCollection];
    }
    return refcadastraleCollection;
  }

  protected convertDateFromClient(refcadastrale: IRefcadastrale): IRefcadastrale {
    return Object.assign({}, refcadastrale, {
      dateBornage: refcadastrale.dateBornage?.isValid() ? refcadastrale.dateBornage.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateBornage = res.body.dateBornage ? dayjs(res.body.dateBornage) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((refcadastrale: IRefcadastrale) => {
        refcadastrale.dateBornage = refcadastrale.dateBornage ? dayjs(refcadastrale.dateBornage) : undefined;
      });
    }
    return res;
  }
}
