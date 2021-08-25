import { IDepartement } from 'app/entities/departement/departement.model';

export interface IRegion {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  departements?: IDepartement[] | null;
}

export class Region implements IRegion {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public departements?: IDepartement[] | null
  ) {}
}

export function getRegionIdentifier(region: IRegion): number | undefined {
  return region.id;
}
