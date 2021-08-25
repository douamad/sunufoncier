import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEvaluationBatiments, EvaluationBatiments } from '../evaluation-batiments.model';

import { EvaluationBatimentsService } from './evaluation-batiments.service';

describe('Service Tests', () => {
  describe('EvaluationBatiments Service', () => {
    let service: EvaluationBatimentsService;
    let httpMock: HttpTestingController;
    let elemDefault: IEvaluationBatiments;
    let expectedResult: IEvaluationBatiments | IEvaluationBatiments[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EvaluationBatimentsService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        niveau: 0,
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

      it('should create a EvaluationBatiments', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EvaluationBatiments()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EvaluationBatiments', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            niveau: 1,
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

      it('should partial update a EvaluationBatiments', () => {
        const patchObject = Object.assign(
          {
            niveau: 1,
            surface: 1,
          },
          new EvaluationBatiments()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EvaluationBatiments', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            niveau: 1,
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

      it('should delete a EvaluationBatiments', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEvaluationBatimentsToCollectionIfMissing', () => {
        it('should add a EvaluationBatiments to an empty array', () => {
          const evaluationBatiments: IEvaluationBatiments = { id: 123 };
          expectedResult = service.addEvaluationBatimentsToCollectionIfMissing([], evaluationBatiments);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(evaluationBatiments);
        });

        it('should not add a EvaluationBatiments to an array that contains it', () => {
          const evaluationBatiments: IEvaluationBatiments = { id: 123 };
          const evaluationBatimentsCollection: IEvaluationBatiments[] = [
            {
              ...evaluationBatiments,
            },
            { id: 456 },
          ];
          expectedResult = service.addEvaluationBatimentsToCollectionIfMissing(evaluationBatimentsCollection, evaluationBatiments);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EvaluationBatiments to an array that doesn't contain it", () => {
          const evaluationBatiments: IEvaluationBatiments = { id: 123 };
          const evaluationBatimentsCollection: IEvaluationBatiments[] = [{ id: 456 }];
          expectedResult = service.addEvaluationBatimentsToCollectionIfMissing(evaluationBatimentsCollection, evaluationBatiments);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(evaluationBatiments);
        });

        it('should add only unique EvaluationBatiments to an array', () => {
          const evaluationBatimentsArray: IEvaluationBatiments[] = [{ id: 123 }, { id: 456 }, { id: 52222 }];
          const evaluationBatimentsCollection: IEvaluationBatiments[] = [{ id: 123 }];
          expectedResult = service.addEvaluationBatimentsToCollectionIfMissing(evaluationBatimentsCollection, ...evaluationBatimentsArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const evaluationBatiments: IEvaluationBatiments = { id: 123 };
          const evaluationBatiments2: IEvaluationBatiments = { id: 456 };
          expectedResult = service.addEvaluationBatimentsToCollectionIfMissing([], evaluationBatiments, evaluationBatiments2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(evaluationBatiments);
          expect(expectedResult).toContain(evaluationBatiments2);
        });

        it('should accept null and undefined values', () => {
          const evaluationBatiments: IEvaluationBatiments = { id: 123 };
          expectedResult = service.addEvaluationBatimentsToCollectionIfMissing([], null, evaluationBatiments, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(evaluationBatiments);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
