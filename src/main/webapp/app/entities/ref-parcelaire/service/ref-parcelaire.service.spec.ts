import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRefParcelaire, RefParcelaire } from '../ref-parcelaire.model';

import { RefParcelaireService } from './ref-parcelaire.service';

describe('Service Tests', () => {
  describe('RefParcelaire Service', () => {
    let service: RefParcelaireService;
    let httpMock: HttpTestingController;
    let elemDefault: IRefParcelaire;
    let expectedResult: IRefParcelaire | IRefParcelaire[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RefParcelaireService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        numeroParcelle: 'AAAAAAA',
        natureParcelle: 'AAAAAAA',
        batie: false,
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

      it('should create a RefParcelaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RefParcelaire()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RefParcelaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            numeroParcelle: 'BBBBBB',
            natureParcelle: 'BBBBBB',
            batie: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a RefParcelaire', () => {
        const patchObject = Object.assign(
          {
            natureParcelle: 'BBBBBB',
            batie: true,
          },
          new RefParcelaire()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RefParcelaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            numeroParcelle: 'BBBBBB',
            natureParcelle: 'BBBBBB',
            batie: true,
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

      it('should delete a RefParcelaire', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRefParcelaireToCollectionIfMissing', () => {
        it('should add a RefParcelaire to an empty array', () => {
          const refParcelaire: IRefParcelaire = { id: 123 };
          expectedResult = service.addRefParcelaireToCollectionIfMissing([], refParcelaire);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(refParcelaire);
        });

        it('should not add a RefParcelaire to an array that contains it', () => {
          const refParcelaire: IRefParcelaire = { id: 123 };
          const refParcelaireCollection: IRefParcelaire[] = [
            {
              ...refParcelaire,
            },
            { id: 456 },
          ];
          expectedResult = service.addRefParcelaireToCollectionIfMissing(refParcelaireCollection, refParcelaire);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a RefParcelaire to an array that doesn't contain it", () => {
          const refParcelaire: IRefParcelaire = { id: 123 };
          const refParcelaireCollection: IRefParcelaire[] = [{ id: 456 }];
          expectedResult = service.addRefParcelaireToCollectionIfMissing(refParcelaireCollection, refParcelaire);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(refParcelaire);
        });

        it('should add only unique RefParcelaire to an array', () => {
          const refParcelaireArray: IRefParcelaire[] = [{ id: 123 }, { id: 456 }, { id: 32609 }];
          const refParcelaireCollection: IRefParcelaire[] = [{ id: 123 }];
          expectedResult = service.addRefParcelaireToCollectionIfMissing(refParcelaireCollection, ...refParcelaireArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const refParcelaire: IRefParcelaire = { id: 123 };
          const refParcelaire2: IRefParcelaire = { id: 456 };
          expectedResult = service.addRefParcelaireToCollectionIfMissing([], refParcelaire, refParcelaire2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(refParcelaire);
          expect(expectedResult).toContain(refParcelaire2);
        });

        it('should accept null and undefined values', () => {
          const refParcelaire: IRefParcelaire = { id: 123 };
          expectedResult = service.addRefParcelaireToCollectionIfMissing([], null, refParcelaire, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(refParcelaire);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
