import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorieNature, CategorieNature } from '../categorie-nature.model';

import { CategorieNatureService } from './categorie-nature.service';

describe('Service Tests', () => {
  describe('CategorieNature Service', () => {
    let service: CategorieNatureService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategorieNature;
    let expectedResult: ICategorieNature | ICategorieNature[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CategorieNatureService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nature: 'AAAAAAA',
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

      it('should create a CategorieNature', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CategorieNature()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategorieNature', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nature: 'BBBBBB',
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

      it('should partial update a CategorieNature', () => {
        const patchObject = Object.assign({}, new CategorieNature());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CategorieNature', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nature: 'BBBBBB',
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

      it('should delete a CategorieNature', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCategorieNatureToCollectionIfMissing', () => {
        it('should add a CategorieNature to an empty array', () => {
          const categorieNature: ICategorieNature = { id: 123 };
          expectedResult = service.addCategorieNatureToCollectionIfMissing([], categorieNature);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categorieNature);
        });

        it('should not add a CategorieNature to an array that contains it', () => {
          const categorieNature: ICategorieNature = { id: 123 };
          const categorieNatureCollection: ICategorieNature[] = [
            {
              ...categorieNature,
            },
            { id: 456 },
          ];
          expectedResult = service.addCategorieNatureToCollectionIfMissing(categorieNatureCollection, categorieNature);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CategorieNature to an array that doesn't contain it", () => {
          const categorieNature: ICategorieNature = { id: 123 };
          const categorieNatureCollection: ICategorieNature[] = [{ id: 456 }];
          expectedResult = service.addCategorieNatureToCollectionIfMissing(categorieNatureCollection, categorieNature);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categorieNature);
        });

        it('should add only unique CategorieNature to an array', () => {
          const categorieNatureArray: ICategorieNature[] = [{ id: 123 }, { id: 456 }, { id: 71457 }];
          const categorieNatureCollection: ICategorieNature[] = [{ id: 123 }];
          expectedResult = service.addCategorieNatureToCollectionIfMissing(categorieNatureCollection, ...categorieNatureArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const categorieNature: ICategorieNature = { id: 123 };
          const categorieNature2: ICategorieNature = { id: 456 };
          expectedResult = service.addCategorieNatureToCollectionIfMissing([], categorieNature, categorieNature2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categorieNature);
          expect(expectedResult).toContain(categorieNature2);
        });

        it('should accept null and undefined values', () => {
          const categorieNature: ICategorieNature = { id: 123 };
          expectedResult = service.addCategorieNatureToCollectionIfMissing([], null, categorieNature, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categorieNature);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
