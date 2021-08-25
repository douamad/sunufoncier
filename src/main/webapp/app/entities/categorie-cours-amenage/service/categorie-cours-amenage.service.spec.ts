import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorieCoursAmenage, CategorieCoursAmenage } from '../categorie-cours-amenage.model';

import { CategorieCoursAmenageService } from './categorie-cours-amenage.service';

describe('Service Tests', () => {
  describe('CategorieCoursAmenage Service', () => {
    let service: CategorieCoursAmenageService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategorieCoursAmenage;
    let expectedResult: ICategorieCoursAmenage | ICategorieCoursAmenage[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CategorieCoursAmenageService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        libelle: 'AAAAAAA',
        prixMetreCare: 0,
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

      it('should create a CategorieCoursAmenage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CategorieCoursAmenage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategorieCoursAmenage', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            libelle: 'BBBBBB',
            prixMetreCare: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CategorieCoursAmenage', () => {
        const patchObject = Object.assign(
          {
            libelle: 'BBBBBB',
            prixMetreCare: 1,
          },
          new CategorieCoursAmenage()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CategorieCoursAmenage', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            libelle: 'BBBBBB',
            prixMetreCare: 1,
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

      it('should delete a CategorieCoursAmenage', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCategorieCoursAmenageToCollectionIfMissing', () => {
        it('should add a CategorieCoursAmenage to an empty array', () => {
          const categorieCoursAmenage: ICategorieCoursAmenage = { id: 123 };
          expectedResult = service.addCategorieCoursAmenageToCollectionIfMissing([], categorieCoursAmenage);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categorieCoursAmenage);
        });

        it('should not add a CategorieCoursAmenage to an array that contains it', () => {
          const categorieCoursAmenage: ICategorieCoursAmenage = { id: 123 };
          const categorieCoursAmenageCollection: ICategorieCoursAmenage[] = [
            {
              ...categorieCoursAmenage,
            },
            { id: 456 },
          ];
          expectedResult = service.addCategorieCoursAmenageToCollectionIfMissing(categorieCoursAmenageCollection, categorieCoursAmenage);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CategorieCoursAmenage to an array that doesn't contain it", () => {
          const categorieCoursAmenage: ICategorieCoursAmenage = { id: 123 };
          const categorieCoursAmenageCollection: ICategorieCoursAmenage[] = [{ id: 456 }];
          expectedResult = service.addCategorieCoursAmenageToCollectionIfMissing(categorieCoursAmenageCollection, categorieCoursAmenage);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categorieCoursAmenage);
        });

        it('should add only unique CategorieCoursAmenage to an array', () => {
          const categorieCoursAmenageArray: ICategorieCoursAmenage[] = [{ id: 123 }, { id: 456 }, { id: 58374 }];
          const categorieCoursAmenageCollection: ICategorieCoursAmenage[] = [{ id: 123 }];
          expectedResult = service.addCategorieCoursAmenageToCollectionIfMissing(
            categorieCoursAmenageCollection,
            ...categorieCoursAmenageArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const categorieCoursAmenage: ICategorieCoursAmenage = { id: 123 };
          const categorieCoursAmenage2: ICategorieCoursAmenage = { id: 456 };
          expectedResult = service.addCategorieCoursAmenageToCollectionIfMissing([], categorieCoursAmenage, categorieCoursAmenage2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categorieCoursAmenage);
          expect(expectedResult).toContain(categorieCoursAmenage2);
        });

        it('should accept null and undefined values', () => {
          const categorieCoursAmenage: ICategorieCoursAmenage = { id: 123 };
          expectedResult = service.addCategorieCoursAmenageToCollectionIfMissing([], null, categorieCoursAmenage, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categorieCoursAmenage);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
