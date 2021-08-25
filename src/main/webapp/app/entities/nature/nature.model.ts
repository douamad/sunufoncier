export interface INature {
  id?: number;
  code?: string | null;
  libelle?: string | null;
}

export class Nature implements INature {
  constructor(public id?: number, public code?: string | null, public libelle?: string | null) {}
}

export function getNatureIdentifier(nature: INature): number | undefined {
  return nature.id;
}
