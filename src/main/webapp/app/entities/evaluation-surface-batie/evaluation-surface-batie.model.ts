import { ICategorieBatie } from 'app/entities/categorie-batie/categorie-batie.model';
import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IEvaluationSurfaceBatie {
  id?: number;
  superficieTotale?: number | null;
  superficieBatie?: number | null;
  categorieBatie?: ICategorieBatie | null;
  dossier?: IDossier | null;
}

export class EvaluationSurfaceBatie implements IEvaluationSurfaceBatie {
  constructor(
    public id?: number,
    public superficieTotale?: number | null,
    public superficieBatie?: number | null,
    public categorieBatie?: ICategorieBatie | null,
    public dossier?: IDossier | null
  ) {}
}

export function getEvaluationSurfaceBatieIdentifier(evaluationSurfaceBatie: IEvaluationSurfaceBatie): number | undefined {
  return evaluationSurfaceBatie.id;
}
