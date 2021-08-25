import * as dayjs from 'dayjs';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { IRepresentant } from 'app/entities/representant/representant.model';
import { SituationProprietaire } from 'app/entities/enumerations/situation-proprietaire.model';
import { TypeStructure } from 'app/entities/enumerations/type-structure.model';

export interface IProprietaire {
  id?: number;
  prenom?: string | null;
  nom?: string | null;
  situation?: SituationProprietaire | null;
  gestionDelegue?: boolean | null;
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
  aquisition?: string | null;
  statutPersoneStructure?: string | null;
  typeStructure?: TypeStructure | null;
  nombreHeritiers?: number | null;
  serviceOcupant?: string | null;
  etablssement?: string | null;
  dossiers?: IDossier[] | null;
  representants?: IRepresentant[] | null;
}

export class Proprietaire implements IProprietaire {
  constructor(
    public id?: number,
    public prenom?: string | null,
    public nom?: string | null,
    public situation?: SituationProprietaire | null,
    public gestionDelegue?: boolean | null,
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
    public aquisition?: string | null,
    public statutPersoneStructure?: string | null,
    public typeStructure?: TypeStructure | null,
    public nombreHeritiers?: number | null,
    public serviceOcupant?: string | null,
    public etablssement?: string | null,
    public dossiers?: IDossier[] | null,
    public representants?: IRepresentant[] | null
  ) {
    this.gestionDelegue = this.gestionDelegue ?? false;
    this.personneMorale = this.personneMorale ?? false;
  }
}

export function getProprietaireIdentifier(proprietaire: IProprietaire): number | undefined {
  return proprietaire.id;
}
