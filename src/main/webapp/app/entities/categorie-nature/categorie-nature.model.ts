import { IEvaluationBatiments } from 'app/entities/evaluation-batiments/evaluation-batiments.model';

export interface ICategorieNature {
  id?: number;
  nature?: string | null;
  libelle?: string | null;
  prixMetreCare?: number | null;
  evaluationBatiments?: IEvaluationBatiments[] | null;
}

export class CategorieNature implements ICategorieNature {
  constructor(
    public id?: number,
    public nature?: string | null,
    public libelle?: string | null,
    public prixMetreCare?: number | null,
    public evaluationBatiments?: IEvaluationBatiments[] | null
  ) {}
}

export function getCategorieNatureIdentifier(categorieNature: ICategorieNature): number | undefined {
  return categorieNature.id;
}
