import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILotissement, Lotissement } from '../lotissement.model';

import { LotissementService } from './lotissement.service';

describe('Service Tests', () => {
  describe('Lotissement Service', () => {
    let service: LotissementService;
    let httpMock: HttpTestingController;
    let elemDefault: ILotissement;
    let expectedResult: ILotissement | ILotissement[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(LotissementService);
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

      it('should create a Lotissement', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Lotissement()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Lotissement', () => {
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

      it('should partial update a Lotissement', () => {
        const patchObject = Object.assign(
          {
            libelle: 'BBBBBB',
          },
          new Lotissement()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Lotissement', () => {
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

      it('should delete a Lotissement', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addLotissementToCollectionIfMissing', () => {
        it('should add a Lotissement to an empty array', () => {
          const lotissement: ILotissement = { id: 123 };
          expectedResult = service.addLotissementToCollectionIfMissing([], lotissement);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(lotissement);
        });

        it('should not add a Lotissement to an array that contains it', () => {
          const lotissement: ILotissement = { id: 123 };
          const lotissementCollection: ILotissement[] = [
            {
              ...lotissement,
            },
            { id: 456 },
          ];
          expectedResult = service.addLotissementToCollectionIfMissing(lotissementCollection, lotissement);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Lotissement to an array that doesn't contain it", () => {
          const lotissement: ILotissement = { id: 123 };
          const lotissementCollection: ILotissement[] = [{ id: 456 }];
          expectedResult = service.addLotissementToCollectionIfMissing(lotissementCollection, lotissement);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(lotissement);
        });

        it('should add only unique Lotissement to an array', () => {
          const lotissementArray: ILotissement[] = [{ id: 123 }, { id: 456 }, { id: 3724 }];
          const lotissementCollection: ILotissement[] = [{ id: 123 }];
          expectedResult = service.addLotissementToCollectionIfMissing(lotissementCollection, ...lotissementArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const lotissement: ILotissement = { id: 123 };
          const lotissement2: ILotissement = { id: 456 };
          expectedResult = service.addLotissementToCollectionIfMissing([], lotissement, lotissement2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(lotissement);
          expect(expectedResult).toContain(lotissement2);
        });

        it('should accept null and undefined values', () => {
          const lotissement: ILotissement = { id: 123 };
          expectedResult = service.addLotissementToCollectionIfMissing([], null, lotissement, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(lotissement);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
