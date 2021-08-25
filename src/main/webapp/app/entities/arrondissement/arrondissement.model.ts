import { ICommune } from 'app/entities/commune/commune.model';
import { IDepartement } from 'app/entities/departement/departement.model';

export interface IArrondissement {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  communes?: ICommune[] | null;
  departement?: IDepartement | null;
}

export class Arrondissement implements IArrondissement {
  constructor(
    public id?: number,
    public code?: string | null,
    public libelle?: string | null,
    public communes?: ICommune[] | null,
    public departement?: IDepartement | null
  ) {}
}

export function getArrondissementIdentifier(arrondissement: IArrondissement): number | undefined {
  return arrondissement.id;
}
