import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';
import { IRegion } from 'app/entities/region/region.model';

export interface IDepartement {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  arrondissements?: IArrondissement[] | null;
  region?: IRegion | null;
}

export class Departement implements IDepartement {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public arrondissements?: IArrondissement[] | null,
    public region?: IRegion | null
  ) {}
}

export function getDepartementIdentifier(departement: IDepartement): number | undefined {
  return departement.id;
}
