import { IEvaluationCloture } from 'app/entities/evaluation-cloture/evaluation-cloture.model';

export interface ICategorieCloture {
  id?: number;
  libelle?: string | null;
  prixMetreCare?: number | null;
  evaluationClotures?: IEvaluationCloture[] | null;
}

export class CategorieCloture implements ICategorieCloture {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public prixMetreCare?: number | null,
    public evaluationClotures?: IEvaluationCloture[] | null
  ) {}
}

export function getCategorieClotureIdentifier(categorieCloture: ICategorieCloture): number | undefined {
  return categorieCloture.id;
}
