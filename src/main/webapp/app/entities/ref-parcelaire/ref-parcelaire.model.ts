import { IDossier } from 'app/entities/dossier/dossier.model';
import { ICommune } from 'app/entities/commune/commune.model';

export interface IRefParcelaire {
  id?: number;
  numeroParcelle?: string | null;
  natureParcelle?: string | null;
  batie?: boolean | null;
  dossiers?: IDossier[] | null;
  commune?: ICommune | null;
}

export class RefParcelaire implements IRefParcelaire {
  constructor(
    public id?: number,
    public numeroParcelle?: string | null,
    public natureParcelle?: string | null,
    public batie?: boolean | null,
    public dossiers?: IDossier[] | null,
    public commune?: ICommune | null
  ) {
    this.batie = this.batie ?? false;
  }
}

export function getRefParcelaireIdentifier(refParcelaire: IRefParcelaire): number | undefined {
  return refParcelaire.id;
}
