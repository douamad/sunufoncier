import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IUsageDossier {
  id?: number;
  code?: string | null;
  libelle?: string | null;
  dossiers?: IDossier[] | null;
}

export class UsageDossier implements IUsageDossier {
  constructor(public id?: number, public code?: string | null, public libelle?: string | null, public dossiers?: IDossier[] | null) {}
}

export function getUsageDossierIdentifier(usageDossier: IUsageDossier): number | undefined {
  return usageDossier.id;
}
