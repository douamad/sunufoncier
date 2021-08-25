import { IQuartier } from 'app/entities/quartier/quartier.model';
import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';
import { IRefParcelaire } from 'app/entities/ref-parcelaire/ref-parcelaire.model';

export interface ICommune {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  quartiers?: IQuartier[] | null;
  arrondissement?: IArrondissement | null;
  refParcelaires?: IRefParcelaire[] | null;
}

export class Commune implements ICommune {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public quartiers?: IQuartier[] | null,
    public arrondissement?: IArrondissement | null,
    public refParcelaires?: IRefParcelaire[] | null
  ) {}
}

export function getCommuneIdentifier(commune: ICommune): number | undefined {
  return commune.id;
}
