import { ICategorieCloture } from 'app/entities/categorie-cloture/categorie-cloture.model';
import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IEvaluationCloture {
  id?: number;
  lineaire?: number | null;
  coeff?: number | null;
  categoriteCloture?: ICategorieCloture | null;
  dossier?: IDossier | null;
}

export class EvaluationCloture implements IEvaluationCloture {
  constructor(
    public id?: number,
    public lineaire?: number | null,
    public coeff?: number | null,
    public categoriteCloture?: ICategorieCloture | null,
    public dossier?: IDossier | null
  ) {}
}

export function getEvaluationClotureIdentifier(evaluationCloture: IEvaluationCloture): number | undefined {
  return evaluationCloture.id;
}
