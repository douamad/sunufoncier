import { ICategorieCoursAmenage } from 'app/entities/categorie-cours-amenage/categorie-cours-amenage.model';
import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IEvaluationCoursAmenage {
  id?: number;
  surface?: number | null;
  coeff?: number | null;
  categorieCoursAmenage?: ICategorieCoursAmenage | null;
  dossier?: IDossier | null;
}

export class EvaluationCoursAmenage implements IEvaluationCoursAmenage {
  constructor(
    public id?: number,
    public surface?: number | null,
    public coeff?: number | null,
    public categorieCoursAmenage?: ICategorieCoursAmenage | null,
    public dossier?: IDossier | null
  ) {}
}

export function getEvaluationCoursAmenageIdentifier(evaluationCoursAmenage: IEvaluationCoursAmenage): number | undefined {
  return evaluationCoursAmenage.id;
}
