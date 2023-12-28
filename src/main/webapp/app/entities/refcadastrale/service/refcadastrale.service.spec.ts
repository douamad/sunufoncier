import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRefcadastrale } from '../refcadastrale.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../refcadastrale.test-samples';

import { RefcadastraleService, RestRefcadastrale } from './refcadastrale.service';

const requireRestSample: RestRefcadastrale = {
  ...sampleWithRequiredData,
  dateBornage: sampleWithRequiredData.dateBornage?.toJSON(),
};

describe('Refcadastrale Service', () => {
  let service: RefcadastraleService;
  let httpMock: HttpTestingController;
  let expectedResult: IRefcadastrale | IRefcadastrale[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(RefcadastraleService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Refcadastrale', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const refcadastrale = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(refcadastrale).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Refcadastrale', () => {
      const refcadastrale = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(refcadastrale).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Refcadastrale', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Refcadastrale', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Refcadastrale', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRefcadastraleToCollectionIfMissing', () => {
      it('should add a Refcadastrale to an empty array', () => {
        const refcadastrale: IRefcadastrale = sampleWithRequiredData;
        expectedResult = service.addRefcadastraleToCollectionIfMissing([], refcadastrale);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(refcadastrale);
      });

      it('should not add a Refcadastrale to an array that contains it', () => {
        const refcadastrale: IRefcadastrale = sampleWithRequiredData;
        const refcadastraleCollection: IRefcadastrale[] = [
          {
            ...refcadastrale,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRefcadastraleToCollectionIfMissing(refcadastraleCollection, refcadastrale);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Refcadastrale to an array that doesn't contain it", () => {
        const refcadastrale: IRefcadastrale = sampleWithRequiredData;
        const refcadastraleCollection: IRefcadastrale[] = [sampleWithPartialData];
        expectedResult = service.addRefcadastraleToCollectionIfMissing(refcadastraleCollection, refcadastrale);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(refcadastrale);
      });

      it('should add only unique Refcadastrale to an array', () => {
        const refcadastraleArray: IRefcadastrale[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const refcadastraleCollection: IRefcadastrale[] = [sampleWithRequiredData];
        expectedResult = service.addRefcadastraleToCollectionIfMissing(refcadastraleCollection, ...refcadastraleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const refcadastrale: IRefcadastrale = sampleWithRequiredData;
        const refcadastrale2: IRefcadastrale = sampleWithPartialData;
        expectedResult = service.addRefcadastraleToCollectionIfMissing([], refcadastrale, refcadastrale2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(refcadastrale);
        expect(expectedResult).toContain(refcadastrale2);
      });

      it('should accept null and undefined values', () => {
        const refcadastrale: IRefcadastrale = sampleWithRequiredData;
        expectedResult = service.addRefcadastraleToCollectionIfMissing([], null, refcadastrale, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(refcadastrale);
      });

      it('should return initial array if no Refcadastrale is added', () => {
        const refcadastraleCollection: IRefcadastrale[] = [sampleWithRequiredData];
        expectedResult = service.addRefcadastraleToCollectionIfMissing(refcadastraleCollection, undefined, null);
        expect(expectedResult).toEqual(refcadastraleCollection);
      });
    });

    describe('compareRefcadastrale', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRefcadastrale(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRefcadastrale(entity1, entity2);
        const compareResult2 = service.compareRefcadastrale(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRefcadastrale(entity1, entity2);
        const compareResult2 = service.compareRefcadastrale(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRefcadastrale(entity1, entity2);
        const compareResult2 = service.compareRefcadastrale(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
