import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IRefcadastrale, Refcadastrale } from '../refcadastrale.model';

import { RefcadastraleService } from './refcadastrale.service';

describe('Service Tests', () => {
  describe('Refcadastrale Service', () => {
    let service: RefcadastraleService;
    let httpMock: HttpTestingController;
    let elemDefault: IRefcadastrale;
    let expectedResult: IRefcadastrale | IRefcadastrale[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RefcadastraleService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        codeSection: 'AAAAAAA',
        codeParcelle: 'AAAAAAA',
        nicad: 'AAAAAAA',
        superfici: 0,
        titreMere: 'AAAAAAA',
        titreCree: 'AAAAAAA',
        numeroRequisition: 'AAAAAAA',
        dateBornage: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateBornage: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Refcadastrale', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateBornage: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBornage: currentDate,
          },
          returnedFromService
        );

        service.create(new Refcadastrale()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Refcadastrale', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            codeSection: 'BBBBBB',
            codeParcelle: 'BBBBBB',
            nicad: 'BBBBBB',
            superfici: 1,
            titreMere: 'BBBBBB',
            titreCree: 'BBBBBB',
            numeroRequisition: 'BBBBBB',
            dateBornage: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBornage: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Refcadastrale', () => {
        const patchObject = Object.assign(
          {
            nicad: 'BBBBBB',
            superfici: 1,
            titreMere: 'BBBBBB',
          },
          new Refcadastrale()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateBornage: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Refcadastrale', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            codeSection: 'BBBBBB',
            codeParcelle: 'BBBBBB',
            nicad: 'BBBBBB',
            superfici: 1,
            titreMere: 'BBBBBB',
            titreCree: 'BBBBBB',
            numeroRequisition: 'BBBBBB',
            dateBornage: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateBornage: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Refcadastrale', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRefcadastraleToCollectionIfMissing', () => {
        it('should add a Refcadastrale to an empty array', () => {
          const refcadastrale: IRefcadastrale = { id: 123 };
          expectedResult = service.addRefcadastraleToCollectionIfMissing([], refcadastrale);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(refcadastrale);
        });

        it('should not add a Refcadastrale to an array that contains it', () => {
          const refcadastrale: IRefcadastrale = { id: 123 };
          const refcadastraleCollection: IRefcadastrale[] = [
            {
              ...refcadastrale,
            },
            { id: 456 },
          ];
          expectedResult = service.addRefcadastraleToCollectionIfMissing(refcadastraleCollection, refcadastrale);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Refcadastrale to an array that doesn't contain it", () => {
          const refcadastrale: IRefcadastrale = { id: 123 };
          const refcadastraleCollection: IRefcadastrale[] = [{ id: 456 }];
          expectedResult = service.addRefcadastraleToCollectionIfMissing(refcadastraleCollection, refcadastrale);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(refcadastrale);
        });

        it('should add only unique Refcadastrale to an array', () => {
          const refcadastraleArray: IRefcadastrale[] = [{ id: 123 }, { id: 456 }, { id: 37828 }];
          const refcadastraleCollection: IRefcadastrale[] = [{ id: 123 }];
          expectedResult = service.addRefcadastraleToCollectionIfMissing(refcadastraleCollection, ...refcadastraleArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const refcadastrale: IRefcadastrale = { id: 123 };
          const refcadastrale2: IRefcadastrale = { id: 456 };
          expectedResult = service.addRefcadastraleToCollectionIfMissing([], refcadastrale, refcadastrale2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(refcadastrale);
          expect(expectedResult).toContain(refcadastrale2);
        });

        it('should accept null and undefined values', () => {
          const refcadastrale: IRefcadastrale = { id: 123 };
          expectedResult = service.addRefcadastraleToCollectionIfMissing([], null, refcadastrale, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(refcadastrale);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
