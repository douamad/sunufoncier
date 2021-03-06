import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { SituationProprietaire } from 'app/entities/enumerations/situation-proprietaire.model';
import { TypeStructure } from 'app/entities/enumerations/type-structure.model';
import { IProprietaire, Proprietaire } from '../proprietaire.model';

import { ProprietaireService } from './proprietaire.service';

describe('Service Tests', () => {
  describe('Proprietaire Service', () => {
    let service: ProprietaireService;
    let httpMock: HttpTestingController;
    let elemDefault: IProprietaire;
    let expectedResult: IProprietaire | IProprietaire[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ProprietaireService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        prenom: 'AAAAAAA',
        nom: 'AAAAAAA',
        situation: SituationProprietaire.RESIDENT,
        gestionDelegue: false,
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
        aquisition: 'AAAAAAA',
        statutPersoneStructure: 'AAAAAAA',
        typeStructure: TypeStructure.ENTREPRISE_INDIVIDUEL,
        nombreHeritiers: 0,
        serviceOcupant: 'AAAAAAA',
        etablssement: 'AAAAAAA',
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

      it('should create a Proprietaire', () => {
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

        service.create(new Proprietaire()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Proprietaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            situation: 'BBBBBB',
            gestionDelegue: true,
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
            aquisition: 'BBBBBB',
            statutPersoneStructure: 'BBBBBB',
            typeStructure: 'BBBBBB',
            nombreHeritiers: 1,
            serviceOcupant: 'BBBBBB',
            etablssement: 'BBBBBB',
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

      it('should partial update a Proprietaire', () => {
        const patchObject = Object.assign(
          {
            nom: 'BBBBBB',
            situation: 'BBBBBB',
            raisonSocial: 'BBBBBB',
            personneMorale: true,
            lieuNaissance: 'BBBBBB',
            numCNI: 'BBBBBB',
            adresse: 'BBBBBB',
            telephone2: 'BBBBBB',
            telephone3: 'BBBBBB',
            typeStructure: 'BBBBBB',
            nombreHeritiers: 1,
            serviceOcupant: 'BBBBBB',
          },
          new Proprietaire()
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

      it('should return a list of Proprietaire', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            prenom: 'BBBBBB',
            nom: 'BBBBBB',
            situation: 'BBBBBB',
            gestionDelegue: true,
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
            aquisition: 'BBBBBB',
            statutPersoneStructure: 'BBBBBB',
            typeStructure: 'BBBBBB',
            nombreHeritiers: 1,
            serviceOcupant: 'BBBBBB',
            etablssement: 'BBBBBB',
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

      it('should delete a Proprietaire', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addProprietaireToCollectionIfMissing', () => {
        it('should add a Proprietaire to an empty array', () => {
          const proprietaire: IProprietaire = { id: 123 };
          expectedResult = service.addProprietaireToCollectionIfMissing([], proprietaire);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(proprietaire);
        });

        it('should not add a Proprietaire to an array that contains it', () => {
          const proprietaire: IProprietaire = { id: 123 };
          const proprietaireCollection: IProprietaire[] = [
            {
              ...proprietaire,
            },
            { id: 456 },
          ];
          expectedResult = service.addProprietaireToCollectionIfMissing(proprietaireCollection, proprietaire);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Proprietaire to an array that doesn't contain it", () => {
          const proprietaire: IProprietaire = { id: 123 };
          const proprietaireCollection: IProprietaire[] = [{ id: 456 }];
          expectedResult = service.addProprietaireToCollectionIfMissing(proprietaireCollection, proprietaire);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(proprietaire);
        });

        it('should add only unique Proprietaire to an array', () => {
          const proprietaireArray: IProprietaire[] = [{ id: 123 }, { id: 456 }, { id: 94089 }];
          const proprietaireCollection: IProprietaire[] = [{ id: 123 }];
          expectedResult = service.addProprietaireToCollectionIfMissing(proprietaireCollection, ...proprietaireArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const proprietaire: IProprietaire = { id: 123 };
          const proprietaire2: IProprietaire = { id: 456 };
          expectedResult = service.addProprietaireToCollectionIfMissing([], proprietaire, proprietaire2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(proprietaire);
          expect(expectedResult).toContain(proprietaire2);
        });

        it('should accept null and undefined values', () => {
          const proprietaire: IProprietaire = { id: 123 };
          expectedResult = service.addProprietaireToCollectionIfMissing([], null, proprietaire, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(proprietaire);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
