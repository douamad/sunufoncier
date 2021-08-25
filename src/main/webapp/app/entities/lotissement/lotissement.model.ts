import { IDossier } from 'app/entities/dossier/dossier.model';
import { IQuartier } from 'app/entities/quartier/quartier.model';

export interface ILotissement {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  lotissements?: IDossier[] | null;
  quartier?: IQuartier | null;
}

export class Lotissement implements ILotissement {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public lotissements?: IDossier[] | null,
    public quartier?: IQuartier | null
  ) {}
}

export function getLotissementIdentifier(lotissement: ILotissement): number | undefined {
  return lotissement.id;
}
