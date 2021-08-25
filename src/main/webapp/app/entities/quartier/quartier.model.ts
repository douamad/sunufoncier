import { ILotissement } from 'app/entities/lotissement/lotissement.model';
import { ICommune } from 'app/entities/commune/commune.model';

export interface IQuartier {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  lotissements?: ILotissement[] | null;
  communune?: ICommune | null;
}

export class Quartier implements IQuartier {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public lotissements?: ILotissement[] | null,
    public communune?: ICommune | null
  ) {}
}

export function getQuartierIdentifier(quartier: IQuartier): number | undefined {
  return quartier.id;
}
