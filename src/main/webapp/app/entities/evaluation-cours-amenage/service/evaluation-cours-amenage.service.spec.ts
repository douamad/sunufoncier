import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEvaluationCoursAmenage, EvaluationCoursAmenage } from '../evaluation-cours-amenage.model';

import { EvaluationCoursAmenageService } from './evaluation-cours-amenage.service';

describe('Service Tests', () => {
  describe('EvaluationCoursAmenage Service', () => {
    let service: EvaluationCoursAmenageService;
    let httpMock: HttpTestingController;
    let elemDefault: IEvaluationCoursAmenage;
    let expectedResult: IEvaluationCoursAmenage | IEvaluationCoursAmenage[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EvaluationCoursAmenageService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        surface: 0,
        coeff: 0,
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

      it('should create a EvaluationCoursAmenage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EvaluationCoursAmenage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EvaluationCoursAmenage', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            surface: 1,
            coeff: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EvaluationCoursAmenage', () => {
        const patchObject = Object.assign(
          {
            coeff: 1,
          },
          new EvaluationCoursAmenage()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EvaluationCoursAmenage', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            surface: 1,
            coeff: 1,
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

      it('should delete a EvaluationCoursAmenage', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEvaluationCoursAmenageToCollectionIfMissing', () => {
        it('should add a EvaluationCoursAmenage to an empty array', () => {
          const evaluationCoursAmenage: IEvaluationCoursAmenage = { id: 123 };
          expectedResult = service.addEvaluationCoursAmenageToCollectionIfMissing([], evaluationCoursAmenage);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(evaluationCoursAmenage);
        });

        it('should not add a EvaluationCoursAmenage to an array that contains it', () => {
          const evaluationCoursAmenage: IEvaluationCoursAmenage = { id: 123 };
          const evaluationCoursAmenageCollection: IEvaluationCoursAmenage[] = [
            {
              ...evaluationCoursAmenage,
            },
            { id: 456 },
          ];
          expectedResult = service.addEvaluationCoursAmenageToCollectionIfMissing(evaluationCoursAmenageCollection, evaluationCoursAmenage);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EvaluationCoursAmenage to an array that doesn't contain it", () => {
          const evaluationCoursAmenage: IEvaluationCoursAmenage = { id: 123 };
          const evaluationCoursAmenageCollection: IEvaluationCoursAmenage[] = [{ id: 456 }];
          expectedResult = service.addEvaluationCoursAmenageToCollectionIfMissing(evaluationCoursAmenageCollection, evaluationCoursAmenage);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(evaluationCoursAmenage);
        });

        it('should add only unique EvaluationCoursAmenage to an array', () => {
          const evaluationCoursAmenageArray: IEvaluationCoursAmenage[] = [{ id: 123 }, { id: 456 }, { id: 47363 }];
          const evaluationCoursAmenageCollection: IEvaluationCoursAmenage[] = [{ id: 123 }];
          expectedResult = service.addEvaluationCoursAmenageToCollectionIfMissing(
            evaluationCoursAmenageCollection,
            ...evaluationCoursAmenageArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const evaluationCoursAmenage: IEvaluationCoursAmenage = { id: 123 };
          const evaluationCoursAmenage2: IEvaluationCoursAmenage = { id: 456 };
          expectedResult = service.addEvaluationCoursAmenageToCollectionIfMissing([], evaluationCoursAmenage, evaluationCoursAmenage2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(evaluationCoursAmenage);
          expect(expectedResult).toContain(evaluationCoursAmenage2);
        });

        it('should accept null and undefined values', () => {
          const evaluationCoursAmenage: IEvaluationCoursAmenage = { id: 123 };
          expectedResult = service.addEvaluationCoursAmenageToCollectionIfMissing([], null, evaluationCoursAmenage, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(evaluationCoursAmenage);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
