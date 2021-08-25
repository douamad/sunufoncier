import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUsageDossier, UsageDossier } from '../usage-dossier.model';

import { UsageDossierService } from './usage-dossier.service';

describe('Service Tests', () => {
  describe('UsageDossier Service', () => {
    let service: UsageDossierService;
    let httpMock: HttpTestingController;
    let elemDefault: IUsageDossier;
    let expectedResult: IUsageDossier | IUsageDossier[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(UsageDossierService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        code: 'AAAAAAA',
        libelle: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UsageDossier', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UsageDossier()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UsageDossier', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            libelle: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a UsageDossier', () => {
        const patchObject = Object.assign(
          {
            code: 'BBBBBB',
          },
          new UsageDossier()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UsageDossier', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            libelle: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UsageDossier', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addUsageDossierToCollectionIfMissing', () => {
        it('should add a UsageDossier to an empty array', () => {
          const usageDossier: IUsageDossier = { id: 123 };
          expectedResult = service.addUsageDossierToCollectionIfMissing([], usageDossier);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(usageDossier);
        });

        it('should not add a UsageDossier to an array that contains it', () => {
          const usageDossier: IUsageDossier = { id: 123 };
          const usageDossierCollection: IUsageDossier[] = [
            {
              ...usageDossier,
            },
            { id: 456 },
          ];
          expectedResult = service.addUsageDossierToCollectionIfMissing(usageDossierCollection, usageDossier);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a UsageDossier to an array that doesn't contain it", () => {
          const usageDossier: IUsageDossier = { id: 123 };
          const usageDossierCollection: IUsageDossier[] = [{ id: 456 }];
          expectedResult = service.addUsageDossierToCollectionIfMissing(usageDossierCollection, usageDossier);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(usageDossier);
        });

        it('should add only unique UsageDossier to an array', () => {
          const usageDossierArray: IUsageDossier[] = [{ id: 123 }, { id: 456 }, { id: 83917 }];
          const usageDossierCollection: IUsageDossier[] = [{ id: 123 }];
          expectedResult = service.addUsageDossierToCollectionIfMissing(usageDossierCollection, ...usageDossierArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const usageDossier: IUsageDossier = { id: 123 };
          const usageDossier2: IUsageDossier = { id: 456 };
          expectedResult = service.addUsageDossierToCollectionIfMissing([], usageDossier, usageDossier2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(usageDossier);
          expect(expectedResult).toContain(usageDossier2);
        });

        it('should accept null and undefined values', () => {
          const usageDossier: IUsageDossier = { id: 123 };
          expectedResult = service.addUsageDossierToCollectionIfMissing([], null, usageDossier, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(usageDossier);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
