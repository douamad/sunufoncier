import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICategorieBatie, CategorieBatie } from '../categorie-batie.model';

import { CategorieBatieService } from './categorie-batie.service';

describe('Service Tests', () => {
  describe('CategorieBatie Service', () => {
    let service: CategorieBatieService;
    let httpMock: HttpTestingController;
    let elemDefault: ICategorieBatie;
    let expectedResult: ICategorieBatie | ICategorieBatie[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CategorieBatieService);
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

      it('should create a CategorieBatie', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CategorieBatie()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CategorieBatie', () => {
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

      it('should partial update a CategorieBatie', () => {
        const patchObject = Object.assign(
          {
            prixMetreCare: 1,
          },
          new CategorieBatie()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CategorieBatie', () => {
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

      it('should delete a CategorieBatie', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCategorieBatieToCollectionIfMissing', () => {
        it('should add a CategorieBatie to an empty array', () => {
          const categorieBatie: ICategorieBatie = { id: 123 };
          expectedResult = service.addCategorieBatieToCollectionIfMissing([], categorieBatie);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categorieBatie);
        });

        it('should not add a CategorieBatie to an array that contains it', () => {
          const categorieBatie: ICategorieBatie = { id: 123 };
          const categorieBatieCollection: ICategorieBatie[] = [
            {
              ...categorieBatie,
            },
            { id: 456 },
          ];
          expectedResult = service.addCategorieBatieToCollectionIfMissing(categorieBatieCollection, categorieBatie);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CategorieBatie to an array that doesn't contain it", () => {
          const categorieBatie: ICategorieBatie = { id: 123 };
          const categorieBatieCollection: ICategorieBatie[] = [{ id: 456 }];
          expectedResult = service.addCategorieBatieToCollectionIfMissing(categorieBatieCollection, categorieBatie);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categorieBatie);
        });

        it('should add only unique CategorieBatie to an array', () => {
          const categorieBatieArray: ICategorieBatie[] = [{ id: 123 }, { id: 456 }, { id: 38956 }];
          const categorieBatieCollection: ICategorieBatie[] = [{ id: 123 }];
          expectedResult = service.addCategorieBatieToCollectionIfMissing(categorieBatieCollection, ...categorieBatieArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const categorieBatie: ICategorieBatie = { id: 123 };
          const categorieBatie2: ICategorieBatie = { id: 456 };
          expectedResult = service.addCategorieBatieToCollectionIfMissing([], categorieBatie, categorieBatie2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(categorieBatie);
          expect(expectedResult).toContain(categorieBatie2);
        });

        it('should accept null and undefined values', () => {
          const categorieBatie: ICategorieBatie = { id: 123 };
          expectedResult = service.addCategorieBatieToCollectionIfMissing([], null, categorieBatie, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(categorieBatie);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
