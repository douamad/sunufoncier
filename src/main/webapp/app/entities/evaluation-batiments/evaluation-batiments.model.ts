import { ICategorieNature } from 'app/entities/categorie-nature/categorie-nature.model';
import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IEvaluationBatiments {
  id?: number;
  niveau?: number | null;
  surface?: number | null;
  coeff?: number | null;
  categorieNature?: ICategorieNature | null;
  dossier?: IDossier | null;
}

export class EvaluationBatiments implements IEvaluationBatiments {
  constructor(
    public id?: number,
    public niveau?: number | null,
    public surface?: number | null,
    public coeff?: number | null,
    public categorieNature?: ICategorieNature | null,
    public dossier?: IDossier | null
  ) {}
}

export function getEvaluationBatimentsIdentifier(evaluationBatiments: IEvaluationBatiments): number | undefined {
  return evaluationBatiments.id;
}
