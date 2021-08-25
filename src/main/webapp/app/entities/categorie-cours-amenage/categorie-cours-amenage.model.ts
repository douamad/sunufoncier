import { IEvaluationCoursAmenage } from 'app/entities/evaluation-cours-amenage/evaluation-cours-amenage.model';

export interface ICategorieCoursAmenage {
  id?: number;
  libelle?: string | null;
  prixMetreCare?: number | null;
  evaluationCoursAmenages?: IEvaluationCoursAmenage[] | null;
}

export class CategorieCoursAmenage implements ICategorieCoursAmenage {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public prixMetreCare?: number | null,
    public evaluationCoursAmenages?: IEvaluationCoursAmenage[] | null
  ) {}
}

export function getCategorieCoursAmenageIdentifier(categorieCoursAmenage: ICategorieCoursAmenage): number | undefined {
  return categorieCoursAmenage.id;
}
