import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { INature, Nature } from '../nature.model';

import { NatureService } from './nature.service';

describe('Service Tests', () => {
  describe('Nature Service', () => {
    let service: NatureService;
    let httpMock: HttpTestingController;
    let elemDefault: INature;
    let expectedResult: INature | INature[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(NatureService);
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

      it('should create a Nature', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Nature()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Nature', () => {
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

      it('should partial update a Nature', () => {
        const patchObject = Object.assign({}, new Nature());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Nature', () => {
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

      it('should delete a Nature', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addNatureToCollectionIfMissing', () => {
        it('should add a Nature to an empty array', () => {
          const nature: INature = { id: 123 };
          expectedResult = service.addNatureToCollectionIfMissing([], nature);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nature);
        });

        it('should not add a Nature to an array that contains it', () => {
          const nature: INature = { id: 123 };
          const natureCollection: INature[] = [
            {
              ...nature,
            },
            { id: 456 },
          ];
          expectedResult = service.addNatureToCollectionIfMissing(natureCollection, nature);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Nature to an array that doesn't contain it", () => {
          const nature: INature = { id: 123 };
          const natureCollection: INature[] = [{ id: 456 }];
          expectedResult = service.addNatureToCollectionIfMissing(natureCollection, nature);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nature);
        });

        it('should add only unique Nature to an array', () => {
          const natureArray: INature[] = [{ id: 123 }, { id: 456 }, { id: 25939 }];
          const natureCollection: INature[] = [{ id: 123 }];
          expectedResult = service.addNatureToCollectionIfMissing(natureCollection, ...natureArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const nature: INature = { id: 123 };
          const nature2: INature = { id: 456 };
          expectedResult = service.addNatureToCollectionIfMissing([], nature, nature2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(nature);
          expect(expectedResult).toContain(nature2);
        });

        it('should accept null and undefined values', () => {
          const nature: INature = { id: 123 };
          expectedResult = service.addNatureToCollectionIfMissing([], null, nature, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(nature);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
