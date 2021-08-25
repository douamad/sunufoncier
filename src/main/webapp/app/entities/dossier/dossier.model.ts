import { IEvaluationSurfaceBatie } from 'app/entities/evaluation-surface-batie/evaluation-surface-batie.model';
import { IEvaluationBatiments } from 'app/entities/evaluation-batiments/evaluation-batiments.model';
import { IEvaluationCloture } from 'app/entities/evaluation-cloture/evaluation-cloture.model';
import { IEvaluationCoursAmenage } from 'app/entities/evaluation-cours-amenage/evaluation-cours-amenage.model';
import { ILocataire } from 'app/entities/locataire/locataire.model';
import { ILotissement } from 'app/entities/lotissement/lotissement.model';
import { IUsageDossier } from 'app/entities/usage-dossier/usage-dossier.model';
import { IProprietaire } from 'app/entities/proprietaire/proprietaire.model';
import { IRefParcelaire } from 'app/entities/ref-parcelaire/ref-parcelaire.model';
import { IRefcadastrale } from 'app/entities/refcadastrale/refcadastrale.model';

export interface IDossier {
  id?: number;
  numero?: string | null;
  valeurBatie?: number | null;
  valeurVenale?: number | null;
  valeurLocativ?: number | null;
  activite?: string | null;
  evaluationSurfaceBaties?: IEvaluationSurfaceBatie[] | null;
  evaluationBatiments?: IEvaluationBatiments[] | null;
  evaluationClotures?: IEvaluationCloture[] | null;
  evaluationCoursAmenages?: IEvaluationCoursAmenage[] | null;
  locataires?: ILocataire[] | null;
  dossier?: ILotissement | null;
  usageDossier?: IUsageDossier | null;
  proprietaire?: IProprietaire | null;
  refParcelaire?: IRefParcelaire | null;
  refcadastrale?: IRefcadastrale | null;
}

export class Dossier implements IDossier {
  constructor(
    public id?: number,
    public numero?: string | null,
    public valeurBatie?: number | null,
    public valeurVenale?: number | null,
    public valeurLocativ?: number | null,
    public activite?: string | null,
    public evaluationSurfaceBaties?: IEvaluationSurfaceBatie[] | null,
    public evaluationBatiments?: IEvaluationBatiments[] | null,
    public evaluationClotures?: IEvaluationCloture[] | null,
    public evaluationCoursAmenages?: IEvaluationCoursAmenage[] | null,
    public locataires?: ILocataire[] | null,
    public dossier?: ILotissement | null,
    public usageDossier?: IUsageDossier | null,
    public proprietaire?: IProprietaire | null,
    public refParcelaire?: IRefParcelaire | null,
    public refcadastrale?: IRefcadastrale | null
  ) {}
}

export function getDossierIdentifier(dossier: IDossier): number | undefined {
  return dossier.id;
}
