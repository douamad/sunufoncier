import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorieCloture, CategorieCloture } from '../categorie-cloture.model';

import { CategorieClotureService } from './categorie-cloture.service';

describe('Service Tests', () => {
  describe('CategorieCloture Service', () => {
    let service: CategorieClotureService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategorieCloture;
    let expectedResult: ICategorieCloture | ICategorieCloture[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CategorieClotureService);
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

      it('should create a CategorieCloture', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CategorieCloture()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategorieCloture', () => {
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

      it('should partial update a CategorieCloture', () => {
        const patchObject = Object.assign({}, new CategorieCloture());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CategorieCloture', () => {
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

      it('should delete a CategorieCloture', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCategorieClotureToCollectionIfMissing', () => {
        it('should add a CategorieCloture to an empty array', () => {
          const categorieCloture: ICategorieCloture = { id: 123 };
          expectedResult = service.addCategorieClotureToCollectionIfMissing([], categorieCloture);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categorieCloture);
        });

        it('should not add a CategorieCloture to an array that contains it', () => {
          const categorieCloture: ICategorieCloture = { id: 123 };
          const categorieClotureCollection: ICategorieCloture[] = [
            {
              ...categorieCloture,
            },
            { id: 456 },
          ];
          expectedResult = service.addCategorieClotureToCollectionIfMissing(categorieClotureCollection, categorieCloture);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CategorieCloture to an array that doesn't contain it", () => {
          const categorieCloture: ICategorieCloture = { id: 123 };
          const categorieClotureCollection: ICategorieCloture[] = [{ id: 456 }];
          expectedResult = service.addCategorieClotureToCollectionIfMissing(categorieClotureCollection, categorieCloture);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categorieCloture);
        });

        it('should add only unique CategorieCloture to an array', () => {
          const categorieClotureArray: ICategorieCloture[] = [{ id: 123 }, { id: 456 }, { id: 19267 }];
          const categorieClotureCollection: ICategorieCloture[] = [{ id: 123 }];
          expectedResult = service.addCategorieClotureToCollectionIfMissing(categorieClotureCollection, ...categorieClotureArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const categorieCloture: ICategorieCloture = { id: 123 };
          const categorieCloture2: ICategorieCloture = { id: 456 };
          expectedResult = service.addCategorieClotureToCollectionIfMissing([], categorieCloture, categorieCloture2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categorieCloture);
          expect(expectedResult).toContain(categorieCloture2);
        });

        it('should accept null and undefined values', () => {
          const categorieCloture: ICategorieCloture = { id: 123 };
          expectedResult = service.addCategorieClotureToCollectionIfMissing([], null, categorieCloture, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categorieCloture);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
