import * as dayjs from 'dayjs';
import { DependantDomaine } from 'app/entities/enumerations/dependant-domaine.model';
import { TypeBornage } from 'app/entities/enumerations/type-bornage.model';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { ICommune } from 'app/entities/commune/commune.model';
import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';
import { IDepartement } from 'app/entities/departement/departement.model';
import { IRefParcelaire } from 'app/entities/ref-parcelaire/ref-parcelaire.model';

export interface IRefcadastrale {
  id?: number;
  codeSection?: string | null;
  codeParcelle?: string | null;
  nicad?: string | null;
  superfici?: number | null;
  titreMere?: string | null;
  titreCree?: string | null;
  titreFoncier?: boolean | null;
  titreNonImatricule?: boolean | null;
  numeroRequisition?: string | null;
  dateBornage?: dayjs.Dayjs | null;
  dependantDomaine?: keyof typeof DependantDomaine | null;
  numeroDeliberation?: string | null;
  dateDeliberation?: string | null;
  nomGeometre?: string | null;
  issueBornage?: keyof typeof TypeBornage | null;

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
    public titreFoncier?: boolean | null,
    public titreNonImatricule?: boolean | null,
    public dateBornage?: dayjs.Dayjs | null,
    public dependantDomaine?: keyof typeof DependantDomaine | null,
    public numeroDeliberation?: string | null,
    public dateDeliberation?: string | null,
    public nomGeometre?: string | null,
    public issueBornage?: keyof typeof TypeBornage | null
  ) {}
}

export function getRefcadastraleIdentifier(refcadastrale: IRefcadastrale): number | undefined {
  return refcadastrale.id;
}
