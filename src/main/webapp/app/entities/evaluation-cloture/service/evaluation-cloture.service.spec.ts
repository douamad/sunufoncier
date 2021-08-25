import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEvaluationCloture, EvaluationCloture } from '../evaluation-cloture.model';

import { EvaluationClotureService } from './evaluation-cloture.service';

describe('Service Tests', () => {
  describe('EvaluationCloture Service', () => {
    let service: EvaluationClotureService;
    let httpMock: HttpTestingController;
    let elemDefault: IEvaluationCloture;
    let expectedResult: IEvaluationCloture | IEvaluationCloture[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EvaluationClotureService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        lineaire: 0,
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

      it('should create a EvaluationCloture', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EvaluationCloture()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EvaluationCloture', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            lineaire: 1,
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

      it('should partial update a EvaluationCloture', () => {
        const patchObject = Object.assign(
          {
            lineaire: 1,
            coeff: 1,
          },
          new EvaluationCloture()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EvaluationCloture', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            lineaire: 1,
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

      it('should delete a EvaluationCloture', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEvaluationClotureToCollectionIfMissing', () => {
        it('should add a EvaluationCloture to an empty array', () => {
          const evaluationCloture: IEvaluationCloture = { id: 123 };
          expectedResult = service.addEvaluationClotureToCollectionIfMissing([], evaluationCloture);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(evaluationCloture);
        });

        it('should not add a EvaluationCloture to an array that contains it', () => {
          const evaluationCloture: IEvaluationCloture = { id: 123 };
          const evaluationClotureCollection: IEvaluationCloture[] = [
            {
              ...evaluationCloture,
            },
            { id: 456 },
          ];
          expectedResult = service.addEvaluationClotureToCollectionIfMissing(evaluationClotureCollection, evaluationCloture);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EvaluationCloture to an array that doesn't contain it", () => {
          const evaluationCloture: IEvaluationCloture = { id: 123 };
          const evaluationClotureCollection: IEvaluationCloture[] = [{ id: 456 }];
          expectedResult = service.addEvaluationClotureToCollectionIfMissing(evaluationClotureCollection, evaluationCloture);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(evaluationCloture);
        });

        it('should add only unique EvaluationCloture to an array', () => {
          const evaluationClotureArray: IEvaluationCloture[] = [{ id: 123 }, { id: 456 }, { id: 85458 }];
          const evaluationClotureCollection: IEvaluationCloture[] = [{ id: 123 }];
          expectedResult = service.addEvaluationClotureToCollectionIfMissing(evaluationClotureCollection, ...evaluationClotureArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const evaluationCloture: IEvaluationCloture = { id: 123 };
          const evaluationCloture2: IEvaluationCloture = { id: 456 };
          expectedResult = service.addEvaluationClotureToCollectionIfMissing([], evaluationCloture, evaluationCloture2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(evaluationCloture);
          expect(expectedResult).toContain(evaluationCloture2);
        });

        it('should accept null and undefined values', () => {
          const evaluationCloture: IEvaluationCloture = { id: 123 };
          expectedResult = service.addEvaluationClotureToCollectionIfMissing([], null, evaluationCloture, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(evaluationCloture);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
