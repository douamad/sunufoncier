import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEvaluationSurfaceBatie, EvaluationSurfaceBatie } from '../evaluation-surface-batie.model';

import { EvaluationSurfaceBatieService } from './evaluation-surface-batie.service';

describe('Service Tests', () => {
  describe('EvaluationSurfaceBatie Service', () => {
    let service: EvaluationSurfaceBatieService;
    let httpMock: HttpTestingController;
    let elemDefault: IEvaluationSurfaceBatie;
    let expectedResult: IEvaluationSurfaceBatie | IEvaluationSurfaceBatie[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(EvaluationSurfaceBatieService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        superficieTotale: 0,
        superficieBatie: 0,
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

      it('should create a EvaluationSurfaceBatie', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EvaluationSurfaceBatie()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EvaluationSurfaceBatie', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            superficieTotale: 1,
            superficieBatie: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a EvaluationSurfaceBatie', () => {
        const patchObject = Object.assign({}, new EvaluationSurfaceBatie());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EvaluationSurfaceBatie', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            superficieTotale: 1,
            superficieBatie: 1,
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

      it('should delete a EvaluationSurfaceBatie', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addEvaluationSurfaceBatieToCollectionIfMissing', () => {
        it('should add a EvaluationSurfaceBatie to an empty array', () => {
          const evaluationSurfaceBatie: IEvaluationSurfaceBatie = { id: 123 };
          expectedResult = service.addEvaluationSurfaceBatieToCollectionIfMissing([], evaluationSurfaceBatie);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(evaluationSurfaceBatie);
        });

        it('should not add a EvaluationSurfaceBatie to an array that contains it', () => {
          const evaluationSurfaceBatie: IEvaluationSurfaceBatie = { id: 123 };
          const evaluationSurfaceBatieCollection: IEvaluationSurfaceBatie[] = [
            {
              ...evaluationSurfaceBatie,
            },
            { id: 456 },
          ];
          expectedResult = service.addEvaluationSurfaceBatieToCollectionIfMissing(evaluationSurfaceBatieCollection, evaluationSurfaceBatie);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a EvaluationSurfaceBatie to an array that doesn't contain it", () => {
          const evaluationSurfaceBatie: IEvaluationSurfaceBatie = { id: 123 };
          const evaluationSurfaceBatieCollection: IEvaluationSurfaceBatie[] = [{ id: 456 }];
          expectedResult = service.addEvaluationSurfaceBatieToCollectionIfMissing(evaluationSurfaceBatieCollection, evaluationSurfaceBatie);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(evaluationSurfaceBatie);
        });

        it('should add only unique EvaluationSurfaceBatie to an array', () => {
          const evaluationSurfaceBatieArray: IEvaluationSurfaceBatie[] = [{ id: 123 }, { id: 456 }, { id: 97959 }];
          const evaluationSurfaceBatieCollection: IEvaluationSurfaceBatie[] = [{ id: 123 }];
          expectedResult = service.addEvaluationSurfaceBatieToCollectionIfMissing(
            evaluationSurfaceBatieCollection,
            ...evaluationSurfaceBatieArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const evaluationSurfaceBatie: IEvaluationSurfaceBatie = { id: 123 };
          const evaluationSurfaceBatie2: IEvaluationSurfaceBatie = { id: 456 };
          expectedResult = service.addEvaluationSurfaceBatieToCollectionIfMissing([], evaluationSurfaceBatie, evaluationSurfaceBatie2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(evaluationSurfaceBatie);
          expect(expectedResult).toContain(evaluationSurfaceBatie2);
        });

        it('should accept null and undefined values', () => {
          const evaluationSurfaceBatie: IEvaluationSurfaceBatie = { id: 123 };
          expectedResult = service.addEvaluationSurfaceBatieToCollectionIfMissing([], null, evaluationSurfaceBatie, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(evaluationSurfaceBatie);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
