import * as dayjs from 'dayjs';
import { IProprietaire } from 'app/entities/proprietaire/proprietaire.model';
import { TypeStructure } from 'app/entities/enumerations/type-structure.model';

export interface IRepresentant {
  id?: number;
  prenom?: string | null;
  lienProprietaire?: string | null;
  nom?: string | null;
  actif?: boolean | null;
  raisonSocial?: string | null;
  siegeSocial?: string | null;
  personneMorale?: boolean | null;
  dateNaiss?: dayjs.Dayjs | null;
  lieuNaissance?: string | null;
  numCNI?: string | null;
  ninea?: string | null;
  adresse?: string | null;
  email?: string | null;
  telephone?: string | null;
  telephone2?: string | null;
  telephone3?: string | null;
  statutPersoneStructure?: string | null;
  dateDelivrance?: dayjs.Dayjs | null;
  typeStructure?: TypeStructure | null;
  proprietaire?: IProprietaire | null;
}

export class Representant implements IRepresentant {
  constructor(
    public id?: number,
    public prenom?: string | null,
    public lienProprietaire?: string | null,
    public nom?: string | null,
    public actif?: boolean | null,
    public raisonSocial?: string | null,
    public siegeSocial?: string | null,
    public personneMorale?: boolean | null,
    public dateNaiss?: dayjs.Dayjs | null,
    public lieuNaissance?: string | null,
    public numCNI?: string | null,
    public ninea?: string | null,
    public adresse?: string | null,
    public email?: string | null,
    public telephone?: string | null,
    public telephone2?: string | null,
    public telephone3?: string | null,
    public statutPersoneStructure?: string | null,
    public dateDelivrance?: dayjs.Dayjs | null,
    public typeStructure?: TypeStructure | null,
    public proprietaire?: IProprietaire | null
  ) {
    this.actif = this.actif ?? false;
    this.personneMorale = this.personneMorale ?? false;
  }
}

export function getRepresentantIdentifier(representant: IRepresentant): number | undefined {
  return representant.id;
}
