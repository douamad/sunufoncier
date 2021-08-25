import * as dayjs from 'dayjs';
import { IDossier } from 'app/entities/dossier/dossier.model';

export interface IRefcadastrale {
  id?: number;
  codeSection?: string | null;
  codeParcelle?: string | null;
  nicad?: string | null;
  superfici?: number | null;
  titreMere?: string | null;
  titreCree?: string | null;
  numeroRequisition?: string | null;
  dateBornage?: dayjs.Dayjs | null;
  dossiers?: IDossier[] | null;
}

export class Refcadastrale implements IRefcadastrale {
  constructor(
    public id?: number,
    public codeSection?: string | null,
    public codeParcelle?: string | null,
    public nicad?: string | null,
    public superfici?: number | null,
    public titreMere?: string | null,
    public titreCree?: string | null,
    public numeroRequisition?: string | null,
    public dateBornage?: dayjs.Dayjs | null,
    public dossiers?: IDossier[] | null
  ) {}
}

export function getRefcadastraleIdentifier(refcadastrale: IRefcadastrale): number | undefined {
  return refcadastrale.id;
}
