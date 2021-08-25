import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { TypeStructure } from 'app/entities/enumerations/type-structure.model';
import { IRepresentant, Representant } from '../representant.model';

import { RepresentantService } from './representant.service';

describe('Service Tests', () => {
  describe('Representant Service', () => {
    let service: RepresentantService;
    let httpMock: HttpTestingController;
    let elemDefault: IRepresentant;
    let expectedResult: IRepresentant | IRepresentant[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RepresentantService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        prenom: 'AAAAAAA',
        lienProprietaire: 'AAAAAAA',
        nom: 'AAAAAAA',
        actif: false,
        raisonSocial: 'AAAAAAA',
        siegeSocial: 'AAAAAAA',
        personneMorale: false,
        dateNaiss: currentDate,
        lieuNaissance: 'AAAAAAA',
        numCNI: 'AAAAAAA',
        ninea: 'AAAAAAA',
        adresse: 'AAAAAAA',
        email: 'AAAAAAA',
        telephone: 'AAAAAAA',
        telephone2: 'AAAAAAA',
        telephone3: 'AAAAAAA',
        statutPersoneStructure: 'AAAAAAA',
        typeStructure: TypeStructure.ENTREPRISE_INDIVIDUEL,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateNaiss: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Representant', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateNaiss: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaiss: currentDate,
          },
          returnedFromService
        );

        service.create(new Representant()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Representant', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            prenom: 'BBBBBB',
            lienProprietaire: 'BBBBBB',
            nom: 'BBBBBB',
            actif: true,
            raisonSocial: 'BBBBBB',
            siegeSocial: 'BBBBBB',
            personneMorale: true,
            dateNaiss: currentDate.format(DATE_TIME_FORMAT),
            lieuNaissance: 'BBBBBB',
            numCNI: 'BBBBBB',
            ninea: 'BBBBBB',
            adresse: 'BBBBBB',
            email: 'BBBBBB',
            telephone: 'BBBBBB',
            telephone2: 'BBBBBB',
            telephone3: 'BBBBBB',
            statutPersoneStructure: 'BBBBBB',
            typeStructure: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaiss: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Representant', () => {
        const patchObject = Object.assign(
          {
            lienProprietaire: 'BBBBBB',
            actif: true,
            raisonSocial: 'BBBBBB',
            siegeSocial: 'BBBBBB',
            personneMorale: true,
            dateNaiss: currentDate.format(DATE_TIME_FORMAT),
            lieuNaissance: 'BBBBBB',
            numCNI: 'BBBBBB',
            adresse: 'BBBBBB',
            telephone3: 'BBBBBB',
          },
          new Representant()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateNaiss: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Representant', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            prenom: 'BBBBBB',
            lienProprietaire: 'BBBBBB',
            nom: 'BBBBBB',
            actif: true,
            raisonSocial: 'BBBBBB',
            siegeSocial: 'BBBBBB',
            personneMorale: true,
            dateNaiss: currentDate.format(DATE_TIME_FORMAT),
            lieuNaissance: 'BBBBBB',
            numCNI: 'BBBBBB',
            ninea: 'BBBBBB',
            adresse: 'BBBBBB',
            email: 'BBBBBB',
            telephone: 'BBBBBB',
            telephone2: 'BBBBBB',
            telephone3: 'BBBBBB',
            statutPersoneStructure: 'BBBBBB',
            typeStructure: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaiss: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Representant', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRepresentantToCollectionIfMissing', () => {
        it('should add a Representant to an empty array', () => {
          const representant: IRepresentant = { id: 123 };
          expectedResult = service.addRepresentantToCollectionIfMissing([], representant);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(representant);
        });

        it('should not add a Representant to an array that contains it', () => {
          const representant: IRepresentant = { id: 123 };
          const representantCollection: IRepresentant[] = [
            {
              ...representant,
            },
            { id: 456 },
          ];
          expectedResult = service.addRepresentantToCollectionIfMissing(representantCollection, representant);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Representant to an array that doesn't contain it", () => {
          const representant: IRepresentant = { id: 123 };
          const representantCollection: IRepresentant[] = [{ id: 456 }];
          expectedResult = service.addRepresentantToCollectionIfMissing(representantCollection, representant);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(representant);
        });

        it('should add only unique Representant to an array', () => {
          const representantArray: IRepresentant[] = [{ id: 123 }, { id: 456 }, { id: 46901 }];
          const representantCollection: IRepresentant[] = [{ id: 123 }];
          expectedResult = service.addRepresentantToCollectionIfMissing(representantCollection, ...representantArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const representant: IRepresentant = { id: 123 };
          const representant2: IRepresentant = { id: 456 };
          expectedResult = service.addRepresentantToCollectionIfMissing([], representant, representant2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(representant);
          expect(expectedResult).toContain(representant2);
        });

        it('should accept null and undefined values', () => {
          const representant: IRepresentant = { id: 123 };
          expectedResult = service.addRepresentantToCollectionIfMissing([], null, representant, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(representant);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
