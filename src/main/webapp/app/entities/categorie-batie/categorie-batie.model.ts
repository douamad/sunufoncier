import { IEvaluationSurfaceBatie } from 'app/entities/evaluation-surface-batie/evaluation-surface-batie.model';

export interface ICategorieBatie {
  id?: number;
  libelle?: string | null;
  prixMetreCare?: number | null;
  evaluationSurfaceBaties?: IEvaluationSurfaceBatie[] | null;
}

export class CategorieBatie implements ICategorieBatie {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public prixMetreCare?: number | null,
    public evaluationSurfaceBaties?: IEvaluationSurfaceBatie[] | null
  ) {}
}

export function getCategorieBatieIdentifier(categorieBatie: ICategorieBatie): number | undefined {
  return categorieBatie.id;
}
