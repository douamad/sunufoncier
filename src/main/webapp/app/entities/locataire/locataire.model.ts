import { IDossier } from 'app/entities/dossier/dossier.model';

export interface ILocataire {
  id?: number;
  nom?: string | null;
  personne?: boolean | null;
  activite?: string | null;
  ninea?: string | null;
  montant?: number | null;
  dossier?: IDossier | null;
}

export class Locataire implements ILocataire {
  constructor(
    public id?: number,
    public nom?: string | null,
    public personne?: boolean | null,
    public activite?: string | null,
    public ninea?: string | null,
    public montant?: number | null,
    public dossier?: IDossier | null
  ) {
    this.personne = this.personne ?? false;
  }
}

export function getLocataireIdentifier(locataire: ILocataire): number | undefined {
  return locataire.id;
}
