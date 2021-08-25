import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILocataire, Locataire } from '../locataire.model';

import { LocataireService } from './locataire.service';

describe('Service Tests', () => {
  describe('Locataire Service', () => {
    let service: LocataireService;
    let httpMock: HttpTestingController;
    let elemDefault: ILocataire;
    let expectedResult: ILocataire | ILocataire[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(LocataireService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        nom: 'AAAAAAA',
        personne: false,
        activite: 'AAAAAAA',
        ninea: 'AAAAAAA',
        montant: 0,
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

      it('should create a Locataire', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Locataire()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Locataire', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nom: 'BBBBBB',
            personne: true,
            activite: 'BBBBBB',
            ninea: 'BBBBBB',
            montant: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Locataire', () => {
        const patchObject = Object.assign(
          {
            nom: 'BBBBBB',
            activite: 'BBBBBB',
            montant: 1,
          },
          new Locataire()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Locataire', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            nom: 'BBBBBB',
            personne: true,
            activite: 'BBBBBB',
            ninea: 'BBBBBB',
            montant: 1,
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

      it('should delete a Locataire', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addLocataireToCollectionIfMissing', () => {
        it('should add a Locataire to an empty array', () => {
          const locataire: ILocataire = { id: 123 };
          expectedResult = service.addLocataireToCollectionIfMissing([], locataire);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(locataire);
        });

        it('should not add a Locataire to an array that contains it', () => {
          const locataire: ILocataire = { id: 123 };
          const locataireCollection: ILocataire[] = [
            {
              ...locataire,
            },
            { id: 456 },
          ];
          expectedResult = service.addLocataireToCollectionIfMissing(locataireCollection, locataire);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Locataire to an array that doesn't contain it", () => {
          const locataire: ILocataire = { id: 123 };
          const locataireCollection: ILocataire[] = [{ id: 456 }];
          expectedResult = service.addLocataireToCollectionIfMissing(locataireCollection, locataire);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(locataire);
        });

        it('should add only unique Locataire to an array', () => {
          const locataireArray: ILocataire[] = [{ id: 123 }, { id: 456 }, { id: 3962 }];
          const locataireCollection: ILocataire[] = [{ id: 123 }];
          expectedResult = service.addLocataireToCollectionIfMissing(locataireCollection, ...locataireArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const locataire: ILocataire = { id: 123 };
          const locataire2: ILocataire = { id: 456 };
          expectedResult = service.addLocataireToCollectionIfMissing([], locataire, locataire2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(locataire);
          expect(expectedResult).toContain(locataire2);
        });

        it('should accept null and undefined values', () => {
          const locataire: ILocataire = { id: 123 };
          expectedResult = service.addLocataireToCollectionIfMissing([], null, locataire, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(locataire);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
