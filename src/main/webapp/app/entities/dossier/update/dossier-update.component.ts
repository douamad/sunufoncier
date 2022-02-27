import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import * as dayjs from 'dayjs';
import { Dossier, IDossier } from '../dossier.model';
import { DossierService } from '../service/dossier.service';
import { ILotissement } from 'app/entities/lotissement/lotissement.model';
import { LotissementService } from 'app/entities/lotissement/service/lotissement.service';
import { IUsageDossier } from 'app/entities/usage-dossier/usage-dossier.model';
import { UsageDossierService } from 'app/entities/usage-dossier/service/usage-dossier.service';
import { IProprietaire, Proprietaire } from 'app/entities/proprietaire/proprietaire.model';
import { ProprietaireService } from 'app/entities/proprietaire/service/proprietaire.service';
import { IRefParcelaire, RefParcelaire } from 'app/entities/ref-parcelaire/ref-parcelaire.model';
import { RefParcelaireService } from 'app/entities/ref-parcelaire/service/ref-parcelaire.service';
import { IRefcadastrale, Refcadastrale } from 'app/entities/refcadastrale/refcadastrale.model';
import { RefcadastraleService } from 'app/entities/refcadastrale/service/refcadastrale.service';
import { EvaluationBatiments, IEvaluationBatiments } from 'app/entities/evaluation-batiments/evaluation-batiments.model';
import { EvaluationCloture, IEvaluationCloture } from 'app/entities/evaluation-cloture/evaluation-cloture.model';
import { EvaluationSurfaceBatie, IEvaluationSurfaceBatie } from 'app/entities/evaluation-surface-batie/evaluation-surface-batie.model';
import { EvaluationCoursAmenage, IEvaluationCoursAmenage } from 'app/entities/evaluation-cours-amenage/evaluation-cours-amenage.model';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICommune } from 'app/entities/commune/commune.model';
import { CommuneService } from 'app/entities/commune/service/commune.service';
import { ILocataire, Locataire } from 'app/entities/locataire/locataire.model';
import { LocataireService } from 'app/entities/locataire/service/locataire.service';
import { LocataireDeleteDialogComponent } from 'app/entities/locataire/delete/locataire-delete-dialog.component';
import { IRepresentant, Representant } from 'app/entities/representant/representant.model';
import { ICategorieBatie } from 'app/entities/categorie-batie/categorie-batie.model';
import { ICategorieCoursAmenage } from 'app/entities/categorie-cours-amenage/categorie-cours-amenage.model';
import { ICategorieCloture } from 'app/entities/categorie-cloture/categorie-cloture.model';
import { ICategorieNature } from 'app/entities/categorie-nature/categorie-nature.model';
import { EvaluationBatimentsService } from 'app/entities/evaluation-batiments/service/evaluation-batiments.service';
import { CategorieNatureService } from 'app/entities/categorie-nature/service/categorie-nature.service';
import { EvaluationClotureService } from 'app/entities/evaluation-cloture/service/evaluation-cloture.service';
import { CategorieClotureService } from 'app/entities/categorie-cloture/service/categorie-cloture.service';
import { EvaluationCoursAmenageService } from 'app/entities/evaluation-cours-amenage/service/evaluation-cours-amenage.service';
import { CategorieCoursAmenageService } from 'app/entities/categorie-cours-amenage/service/categorie-cours-amenage.service';
import { EvaluationSurfaceBatieService } from 'app/entities/evaluation-surface-batie/service/evaluation-surface-batie.service';
import { CategorieBatieService } from 'app/entities/categorie-batie/service/categorie-batie.service';
import { EvaluationBatimentsDeleteDialogComponent } from 'app/entities/evaluation-batiments/delete/evaluation-batiments-delete-dialog.component';
import { EvaluationClotureDeleteDialogComponent } from 'app/entities/evaluation-cloture/delete/evaluation-cloture-delete-dialog.component';
import { EvaluationCoursAmenageDeleteDialogComponent } from 'app/entities/evaluation-cours-amenage/delete/evaluation-cours-amenage-delete-dialog.component';
import { EvaluationSurfaceBatieDeleteDialogComponent } from 'app/entities/evaluation-surface-batie/delete/evaluation-surface-batie-delete-dialog.component';

@Component({
  selector: 'jhi-dossier-update',
  templateUrl: './dossier-update.component.html',
})
export class DossierUpdateComponent implements OnInit {
  isSaving = false;
  locataires: ILocataire[] = [];
  evaluationBatiments?: IEvaluationBatiments[];
  evaluationClotures?: IEvaluationCloture[];
  evaluationCoursAmenages?: IEvaluationCoursAmenage[];
  evaluationSurfaceBaties?: IEvaluationSurfaceBatie[];

  lotissementsSharedCollection: ILotissement[] = [];
  usageDossiersSharedCollection: IUsageDossier[] = [];
  proprietairesSharedCollection: IProprietaire[] = [];
  refParcelairesSharedCollection: IRefParcelaire[] = [];
  refcadastralesSharedCollection: IRefcadastrale[] = [];
  communesSharedCollection: ICommune[] = [];
  categorieBatiesSharedCollection: ICategorieBatie[] = [];
  categorieCoursAmenagesSharedCollection: ICategorieCoursAmenage[] = [];
  categorieCloturesSharedCollection: ICategorieCloture[] = [];
  categorieNaturesSharedCollection: ICategorieNature[] = [];

  editDossierForm = this.fb.group({
    id: [],
    numero: [],
    valeurBatie: [],
    valeurVenale: [],
    valeurLocativ: [],
    activite: [],
    dossier: [],
    usageDossier: [],
    proprietaire: [],
    refParcelaire: [],
    refcadastrale: [],
  });
  editEBForm = this.fb.group({
    id: [],
    niveau: [],
    surface: [],
    coeff: [],
    categorieNature: [],
    dossier: [],
  });
  editECForm = this.fb.group({
    id: [],
    lineaire: [],
    coeff: [],
    categoriteCloture: [],
    dossier: [],
  });
  editECAForm = this.fb.group({
    id: [],
    surface: [],
    coeff: [],
    categorieCoursAmenage: [],
    dossier: [],
  });
  editESBForm = this.fb.group({
    id: [],
    superficieTotale: [],
    superficieBatie: [],
    categorieBatie: [],
    dossier: [],
  });
  editRPForm = this.fb.group({
    id: [],
    numeroParcelle: [],
    natureParcelle: [],
    batie: [],
    commune: [],
  });
  editRCForm = this.fb.group({
    id: [],
    codeSection: [],
    codeParcelle: [],
    nicad: [],
    superfici: [],
    titreMere: [],
    titreCree: [],
    numeroRequisition: [],
    dateBornage: [],
  });
  editProprioForm = this.fb.group({
    id: [],
    prenom: [],
    nom: [],
    situation: [],
    gestionDelegue: [],
    raisonSocial: [],
    siegeSocial: [],
    personneMorale: [],
    dateNaiss: [],
    lieuNaissance: [],
    numCNI: [],
    ninea: [],
    adresse: [],
    email: [],
    telephone: [],
    telephone2: [],
    telephone3: [],
    aquisition: [],
    statutPersoneStructure: [],
    typeStructure: [],
    nombreHeritiers: [],
    serviceOcupant: [],
    etablssement: [],
  });
  editRepForm = this.fb.group({
    id: [],
    prenom: [],
    lienProprietaire: [],
    nom: [],
    actif: [],
    raisonSocial: [],
    siegeSocial: [],
    personneMorale: [],
    dateNaiss: [],
    lieuNaissance: [],
    numCNI: [],
    ninea: [],
    adresse: [],
    email: [],
    telephone: [],
    telephone2: [],
    telephone3: [],
    statutPersoneStructure: [],
    typeStructure: [],
    proprietaire: [],
  });
  editLocataireForm = this.fb.group({
    id: [],
    nom: ['', [Validators.required]],
    personne: [],
    activite: [],
    ninea: [],
    montant: ['', [Validators.required]],
    dossier: [],
  });
  idDossier: number | undefined;
  constructor(
    protected dossierService: DossierService,
    protected lotissementService: LotissementService,
    protected usageDossierService: UsageDossierService,
    protected proprietaireService: ProprietaireService,
    protected refParcelaireService: RefParcelaireService,
    protected refcadastraleService: RefcadastraleService,
    protected evaluationBatimentsService: EvaluationBatimentsService,
    protected categorieNatureService: CategorieNatureService,
    protected evaluationClotureService: EvaluationClotureService,
    protected categorieClotureService: CategorieClotureService,
    protected evaluationCoursAmenageService: EvaluationCoursAmenageService,
    protected categorieCoursAmenageService: CategorieCoursAmenageService,
    protected evaluationSurfaceBatieService: EvaluationSurfaceBatieService,
    protected categorieBatieService: CategorieBatieService,
    protected communeService: CommuneService,
    protected locataireService: LocataireService,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dossier }) => {
      this.updateForm(dossier);
      if (dossier.id) {
        this.idDossier = dossier.id;
      }
      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dossier = this.createFromForm();
    if (dossier.id !== undefined) {
      this.subscribeToSaveResponse(this.dossierService.update(dossier));
    } else {
      this.subscribeToSaveResponse(this.dossierService.create(dossier));
    }
  }

  trackLotissementById(index: number, item: ILotissement): number {
    return item.id!;
  }

  trackUsageDossierById(index: number, item: IUsageDossier): number {
    return item.id!;
  }

  trackProprietaireById(index: number, item: IProprietaire): number {
    return item.id!;
  }

  trackRefParcelaireById(index: number, item: IRefParcelaire): number {
    return item.id!;
  }

  trackRefcadastraleById(index: number, item: IRefcadastrale): number {
    return item.id!;
  }

  trackCommuneById(index: number, item: ICommune): number {
    return item.id!;
  }
  trackCategorieNatureById(index: number, item: ICategorieNature): number {
    return item.id!;
  }
  trackCategorieClotureById(index: number, item: ICategorieCloture): number {
    return item.id!;
  }
  trackCategorieCoursAmenageById(index: number, item: ICategorieCoursAmenage): number {
    return item.id!;
  }
  trackCategorieBatieById(index: number, item: ICategorieBatie): number {
    return item.id!;
  }
  trackEBId(index: number, item: IEvaluationBatiments): number {
    return item.id!;
  }
  trackECId(index: number, item: IEvaluationCloture): number {
    return item.id!;
  }
  trackECAId(index: number, item: IEvaluationCoursAmenage): number {
    return item.id!;
  }
  trackESBId(index: number, item: IEvaluationSurfaceBatie): number {
    return item.id!;
  }
  loadLocataires(): void {
    if (this.idDossier) {
      this.locataireService
        .query()
        .pipe(map((res: HttpResponse<ILocataire[]>) => res.body ?? []))
        .subscribe((locataires: ILocataire[]) => (this.locataires = locataires));
    }
  }
  loadEB(): void {
    this.evaluationBatimentsService
      .query()
      .pipe(map((res: HttpResponse<IEvaluationBatiments[]>) => res.body ?? []))
      .subscribe((eb: IEvaluationBatiments[]) => (this.evaluationBatiments = eb));
  }

  loadEC(): void {
    this.evaluationClotureService
      .query()
      .pipe(map((res: HttpResponse<IEvaluationCloture[]>) => res.body ?? []))
      .subscribe((ec: IEvaluationCloture[]) => (this.evaluationClotures = ec));
  }

  loadESB(): void {
    this.evaluationSurfaceBatieService
      .query()
      .pipe(map((res: HttpResponse<IEvaluationSurfaceBatie[]>) => res.body ?? []))
      .subscribe((esb: IEvaluationSurfaceBatie[]) => (this.evaluationSurfaceBaties = esb));
  }

  loadECA(): void {
    this.evaluationCoursAmenageService
      .query()
      .pipe(map((res: HttpResponse<IEvaluationCoursAmenage[]>) => res.body ?? []))
      .subscribe((eca: IEvaluationCoursAmenage[]) => (this.evaluationCoursAmenages = eca));
  }
  trackLocataireId(index: number, item: ILocataire): number {
    return item.id!;
  }
  deleteLoacataire(locataire: ILocataire): void {
    const modalRef = this.modalService.open(LocataireDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.locataire = locataire;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadLocataires();
      }
    });
  }
  deleteEB(evaluationBatiments: IEvaluationBatiments): void {
    const modalRef = this.modalService.open(EvaluationBatimentsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.evaluationBatiments = evaluationBatiments;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadEB();
      }
    });
  }
  deleteEC(evaluationCloture: IEvaluationCloture): void {
    const modalRef = this.modalService.open(EvaluationClotureDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.evaluationCloture = evaluationCloture;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadEC();
      }
    });
  }
  deleteECA(evaluationCoursAmenage: IEvaluationCoursAmenage): void {
    const modalRef = this.modalService.open(EvaluationCoursAmenageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.evaluationCoursAmenage = evaluationCoursAmenage;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadECA();
      }
    });
  }
  deleteESB(evaluationSurfaceBatie: IEvaluationSurfaceBatie): void {
    const modalRef = this.modalService.open(EvaluationSurfaceBatieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.evaluationSurfaceBatie = evaluationSurfaceBatie;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadESB();
      }
    });
  }
  setNicad(): void {
    const commune: string = this.editRPForm.get(['commune'])!.value ? this.editRPForm.get(['commune'])!.value.code : '';
    const section: string = this.editRCForm.get(['codeSection'])!.value ? this.editRCForm.get(['codeSection'])!.value : '';
    const parcelle: string = this.editRCForm.get(['codeParcelle'])!.value ? this.editRCForm.get(['codeParcelle'])!.value : '';
    this.editRCForm.get(['nicad'])!.setValue(`${commune}-${section}-${parcelle}`);
  }

  saveLocataire(): void {
    const locataire = this.createLocataireFromForm();
    console.warn(locataire);
    this.locataires.push(locataire);
    console.warn(this.locataires);
    this.modalService.dismissAll();
  }
  ajouterLocataire(content: any): void {
    this.modalService.open(content, { size: 'lg', centered: true });
  }
  resetLocataireForm(): void {
    this.editLocataireForm.patchValue({
      id: null,
      nom: null,
      personne: null,
      activite: null,
      ninea: null,
      montant: null,
      dossier: null,
    });
  }
  createLocataireFromForm(): ILocataire {
    return {
      ...new Locataire(),
      id: this.editLocataireForm.get(['id'])!.value,
      nom: this.editLocataireForm.get(['nom'])!.value,
      personne: this.editLocataireForm.get(['personne'])!.value,
      activite: this.editLocataireForm.get(['activite'])!.value,
      ninea: this.editLocataireForm.get(['ninea'])!.value,
      montant: this.editLocataireForm.get(['montant'])!.value,
      dossier: this.editLocataireForm.get(['dossier'])!.value,
    };
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDossier>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(dossier: IDossier): void {
    this.editDossierForm.patchValue({
      id: dossier.id,
      numero: dossier.numero,
      valeurBatie: dossier.valeurBatie,
      valeurVenale: dossier.valeurVenale,
      valeurLocativ: dossier.valeurLocativ,
      activite: dossier.activite,
      dossier: dossier.dossier,
      usageDossier: dossier.usageDossier,
      proprietaire: dossier.proprietaire,
      refParcelaire: dossier.refParcelaire,
      refcadastrale: dossier.refcadastrale,
    });

    this.lotissementsSharedCollection = this.lotissementService.addLotissementToCollectionIfMissing(
      this.lotissementsSharedCollection,
      dossier.dossier
    );
    this.usageDossiersSharedCollection = this.usageDossierService.addUsageDossierToCollectionIfMissing(
      this.usageDossiersSharedCollection,
      dossier.usageDossier
    );
    this.proprietairesSharedCollection = this.proprietaireService.addProprietaireToCollectionIfMissing(
      this.proprietairesSharedCollection,
      dossier.proprietaire
    );
    this.refParcelairesSharedCollection = this.refParcelaireService.addRefParcelaireToCollectionIfMissing(
      this.refParcelairesSharedCollection,
      dossier.refParcelaire
    );
    this.refcadastralesSharedCollection = this.refcadastraleService.addRefcadastraleToCollectionIfMissing(
      this.refcadastralesSharedCollection,
      dossier.refcadastrale
    );
  }

  protected loadRelationshipsOptions(): void {
    this.lotissementService
      .query()
      .pipe(map((res: HttpResponse<ILotissement[]>) => res.body ?? []))
      .pipe(
        map((lotissements: ILotissement[]) =>
          this.lotissementService.addLotissementToCollectionIfMissing(lotissements, this.editDossierForm.get('dossier')!.value)
        )
      )
      .subscribe((lotissements: ILotissement[]) => (this.lotissementsSharedCollection = lotissements));

    this.usageDossierService
      .query()
      .pipe(map((res: HttpResponse<IUsageDossier[]>) => res.body ?? []))
      .pipe(
        map((usageDossiers: IUsageDossier[]) =>
          this.usageDossierService.addUsageDossierToCollectionIfMissing(usageDossiers, this.editDossierForm.get('usageDossier')!.value)
        )
      )
      .subscribe((usageDossiers: IUsageDossier[]) => (this.usageDossiersSharedCollection = usageDossiers));

    this.proprietaireService
      .query()
      .pipe(map((res: HttpResponse<IProprietaire[]>) => res.body ?? []))
      .pipe(
        map((proprietaires: IProprietaire[]) =>
          this.proprietaireService.addProprietaireToCollectionIfMissing(proprietaires, this.editDossierForm.get('proprietaire')!.value)
        )
      )
      .subscribe((proprietaires: IProprietaire[]) => (this.proprietairesSharedCollection = proprietaires));

    this.refParcelaireService
      .query()
      .pipe(map((res: HttpResponse<IRefParcelaire[]>) => res.body ?? []))
      .pipe(
        map((refParcelaires: IRefParcelaire[]) =>
          this.refParcelaireService.addRefParcelaireToCollectionIfMissing(refParcelaires, this.editDossierForm.get('refParcelaire')!.value)
        )
      )
      .subscribe((refParcelaires: IRefParcelaire[]) => (this.refParcelairesSharedCollection = refParcelaires));

    this.refcadastraleService
      .query()
      .pipe(map((res: HttpResponse<IRefcadastrale[]>) => res.body ?? []))
      .pipe(
        map((refcadastrales: IRefcadastrale[]) =>
          this.refcadastraleService.addRefcadastraleToCollectionIfMissing(refcadastrales, this.editDossierForm.get('refcadastrale')!.value)
        )
      )
      .subscribe((refcadastrales: IRefcadastrale[]) => (this.refcadastralesSharedCollection = refcadastrales));
    this.communeService
      .query()
      .pipe(map((res: HttpResponse<ICommune[]>) => res.body ?? []))
      .pipe(
        map((communes: ICommune[]) => this.communeService.addCommuneToCollectionIfMissing(communes, this.editRPForm.get('commune')!.value))
      )
      .subscribe((communes: ICommune[]) => (this.communesSharedCollection = communes));
    this.loadLocataires();
    this.loadEB();
    this.loadEC();
    this.loadECA();
    this.loadESB();
    this.loadEBRelationshipsOptions();
    this.loadECARelationshipsOptions();
    this.loadECRelationshipsOptions();
    this.loadESBRelationshipsOptions();
  }
  protected loadEBRelationshipsOptions(): void {
    this.categorieNatureService
      .query()
      .pipe(map((res: HttpResponse<ICategorieNature[]>) => res.body ?? []))
      .pipe(
        map((categorieNatures: ICategorieNature[]) =>
          this.categorieNatureService.addCategorieNatureToCollectionIfMissing(
            categorieNatures,
            this.editEBForm.get('categorieNature')!.value
          )
        )
      )
      .subscribe((categorieNatures: ICategorieNature[]) => (this.categorieNaturesSharedCollection = categorieNatures));
  }
  protected loadECRelationshipsOptions(): void {
    this.categorieClotureService
      .query()
      .pipe(map((res: HttpResponse<ICategorieCloture[]>) => res.body ?? []))
      .pipe(
        map((categorieClotures: ICategorieCloture[]) =>
          this.categorieClotureService.addCategorieClotureToCollectionIfMissing(
            categorieClotures,
            this.editECForm.get('categoriteCloture')!.value
          )
        )
      )
      .subscribe((categorieClotures: ICategorieCloture[]) => (this.categorieCloturesSharedCollection = categorieClotures));
  }

  protected loadECARelationshipsOptions(): void {
    this.categorieCoursAmenageService
      .query()
      .pipe(map((res: HttpResponse<ICategorieCoursAmenage[]>) => res.body ?? []))
      .pipe(
        map((categorieCoursAmenages: ICategorieCoursAmenage[]) =>
          this.categorieCoursAmenageService.addCategorieCoursAmenageToCollectionIfMissing(
            categorieCoursAmenages,
            this.editECAForm.get('categorieCoursAmenage')!.value
          )
        )
      )
      .subscribe(
        (categorieCoursAmenages: ICategorieCoursAmenage[]) => (this.categorieCoursAmenagesSharedCollection = categorieCoursAmenages)
      );
  }
  protected loadESBRelationshipsOptions(): void {
    this.categorieBatieService
      .query()
      .pipe(map((res: HttpResponse<ICategorieBatie[]>) => res.body ?? []))
      .pipe(
        map((categorieBaties: ICategorieBatie[]) =>
          this.categorieBatieService.addCategorieBatieToCollectionIfMissing(categorieBaties, this.editESBForm.get('categorieBatie')!.value)
        )
      )
      .subscribe((categorieBaties: ICategorieBatie[]) => (this.categorieBatiesSharedCollection = categorieBaties));
  }

  protected createFromForm(): IDossier {
    return {
      ...new Dossier(),
      id: this.editDossierForm.get(['id'])!.value,
      numero: this.editDossierForm.get(['numero'])!.value,
      valeurBatie: this.editDossierForm.get(['valeurBatie'])!.value,
      valeurVenale: this.editDossierForm.get(['valeurVenale'])!.value,
      valeurLocativ: this.editDossierForm.get(['valeurLocativ'])!.value,
      activite: this.editDossierForm.get(['activite'])!.value,
      dossier: this.editDossierForm.get(['dossier'])!.value,
      usageDossier: this.editDossierForm.get(['usageDossier'])!.value,
      proprietaire: this.editDossierForm.get(['proprietaire'])!.value,
      refParcelaire: this.editDossierForm.get(['refParcelaire'])!.value,
      refcadastrale: this.editDossierForm.get(['refcadastrale'])!.value,
    };
  }

  protected updateEBForm(evaluationBatiments: IEvaluationBatiments): void {
    this.editEBForm.patchValue({
      id: evaluationBatiments.id,
      niveau: evaluationBatiments.niveau,
      surface: evaluationBatiments.surface,
      coeff: evaluationBatiments.coeff,
      categorieNature: evaluationBatiments.categorieNature,
      dossier: evaluationBatiments.dossier,
    });
  }
  protected createEBFromForm(): IEvaluationBatiments {
    return {
      ...new EvaluationBatiments(),
      id: this.editEBForm.get(['id'])!.value,
      niveau: this.editEBForm.get(['niveau'])!.value,
      surface: this.editEBForm.get(['surface'])!.value,
      coeff: this.editEBForm.get(['coeff'])!.value,
      categorieNature: this.editEBForm.get(['categorieNature'])!.value,
      dossier: this.editEBForm.get(['dossier'])!.value,
    };
  }

  protected updateECForm(evaluationCloture: IEvaluationCloture): void {
    this.editECForm.patchValue({
      id: evaluationCloture.id,
      lineaire: evaluationCloture.lineaire,
      coeff: evaluationCloture.coeff,
      categoriteCloture: evaluationCloture.categoriteCloture,
      dossier: evaluationCloture.dossier,
    });
  }
  protected createECFromForm(): IEvaluationCloture {
    return {
      ...new EvaluationCloture(),
      id: this.editECForm.get(['id'])!.value,
      lineaire: this.editECForm.get(['lineaire'])!.value,
      coeff: this.editECForm.get(['coeff'])!.value,
      categoriteCloture: this.editECForm.get(['categoriteCloture'])!.value,
      dossier: this.editECForm.get(['dossier'])!.value,
    };
  }
  protected updateECAForm(evaluationCoursAmenage: IEvaluationCoursAmenage): void {
    this.editECAForm.patchValue({
      id: evaluationCoursAmenage.id,
      surface: evaluationCoursAmenage.surface,
      coeff: evaluationCoursAmenage.coeff,
      categorieCoursAmenage: evaluationCoursAmenage.categorieCoursAmenage,
      dossier: evaluationCoursAmenage.dossier,
    });
  }
  protected createECAFromForm(): IEvaluationCoursAmenage {
    return {
      ...new EvaluationCoursAmenage(),
      id: this.editECAForm.get(['id'])!.value,
      surface: this.editECAForm.get(['surface'])!.value,
      coeff: this.editECAForm.get(['coeff'])!.value,
      categorieCoursAmenage: this.editECAForm.get(['categorieCoursAmenage'])!.value,
      dossier: this.editECAForm.get(['dossier'])!.value,
    };
  }

  protected updateESBForm(evaluationSurfaceBatie: IEvaluationSurfaceBatie): void {
    this.editESBForm.patchValue({
      id: evaluationSurfaceBatie.id,
      superficieTotale: evaluationSurfaceBatie.superficieTotale,
      superficieBatie: evaluationSurfaceBatie.superficieBatie,
      categorieBatie: evaluationSurfaceBatie.categorieBatie,
      dossier: evaluationSurfaceBatie.dossier,
    });
  }
  protected createESBFromForm(): IEvaluationSurfaceBatie {
    return {
      ...new EvaluationSurfaceBatie(),
      id: this.editESBForm.get(['id'])!.value,
      superficieTotale: this.editESBForm.get(['superficieTotale'])!.value,
      superficieBatie: this.editESBForm.get(['superficieBatie'])!.value,
      categorieBatie: this.editESBForm.get(['categorieBatie'])!.value,
      dossier: this.editESBForm.get(['dossier'])!.value,
    };
  }
  protected updateRPForm(refParcelaire: IRefParcelaire): void {
    this.editRPForm.patchValue({
      id: refParcelaire.id,
      numeroParcelle: refParcelaire.numeroParcelle,
      natureParcelle: refParcelaire.natureParcelle,
      batie: refParcelaire.batie,
      commune: refParcelaire.commune,
    });
  }
  protected createRPFromForm(): IRefParcelaire {
    return {
      ...new RefParcelaire(),
      id: this.editRPForm.get(['id'])!.value,
      numeroParcelle: this.editRPForm.get(['numeroParcelle'])!.value,
      natureParcelle: this.editRPForm.get(['natureParcelle'])!.value,
      batie: this.editDossierForm.get(['batie'])!.value,
      commune: this.editRPForm.get(['commune'])!.value,
    };
  }
  protected updateRCForm(refcadastrale: IRefcadastrale): void {
    this.editRCForm.patchValue({
      id: refcadastrale.id,
      codeSection: refcadastrale.codeSection,
      codeParcelle: refcadastrale.codeParcelle,
      nicad: refcadastrale.nicad,
      superfici: refcadastrale.superfici,
      titreMere: refcadastrale.titreMere,
      titreCree: refcadastrale.titreCree,
      numeroRequisition: refcadastrale.numeroRequisition,
      dateBornage: refcadastrale.dateBornage ? refcadastrale.dateBornage.format(DATE_TIME_FORMAT) : null,
    });
  }

  protected createRCFromForm(): IRefcadastrale {
    return {
      ...new Refcadastrale(),
      id: this.editRCForm.get(['id'])!.value,
      codeSection: this.editRCForm.get(['codeSection'])!.value,
      codeParcelle: this.editRCForm.get(['codeParcelle'])!.value,
      nicad: this.editRCForm.get(['nicad'])!.value,
      superfici: this.editRCForm.get(['superfici'])!.value,
      titreMere: this.editRCForm.get(['titreMere'])!.value,
      titreCree: this.editRCForm.get(['titreCree'])!.value,
      numeroRequisition: this.editRCForm.get(['numeroRequisition'])!.value,
      dateBornage: this.editRCForm.get(['dateBornage'])!.value
        ? dayjs(this.editRCForm.get(['dateBornage'])!.value, DATE_TIME_FORMAT)
        : undefined,
    };
  }

  protected updateProprioForm(proprietaire: IProprietaire): void {
    this.editProprioForm.patchValue({
      id: proprietaire.id,
      prenom: proprietaire.prenom,
      nom: proprietaire.nom,
      situation: proprietaire.situation,
      gestionDelegue: proprietaire.gestionDelegue,
      raisonSocial: proprietaire.raisonSocial,
      siegeSocial: proprietaire.siegeSocial,
      personneMorale: proprietaire.personneMorale,
      dateNaiss: proprietaire.dateNaiss ? proprietaire.dateNaiss.format(DATE_TIME_FORMAT) : null,
      lieuNaissance: proprietaire.lieuNaissance,
      numCNI: proprietaire.numCNI,
      ninea: proprietaire.ninea,
      adresse: proprietaire.adresse,
      email: proprietaire.email,
      telephone: proprietaire.telephone,
      telephone2: proprietaire.telephone2,
      telephone3: proprietaire.telephone3,
      aquisition: proprietaire.aquisition,
      statutPersoneStructure: proprietaire.statutPersoneStructure,
      typeStructure: proprietaire.typeStructure,
      nombreHeritiers: proprietaire.nombreHeritiers,
      serviceOcupant: proprietaire.serviceOcupant,
      etablssement: proprietaire.etablssement,
    });
  }
  protected createProprioFromForm(): IProprietaire {
    return {
      ...new Proprietaire(),
      id: this.editProprioForm.get(['id'])!.value,
      prenom: this.editProprioForm.get(['prenom'])!.value,
      nom: this.editProprioForm.get(['nom'])!.value,
      situation: this.editProprioForm.get(['situation'])!.value,
      gestionDelegue: this.editProprioForm.get(['gestionDelegue'])!.value,
      raisonSocial: this.editProprioForm.get(['raisonSocial'])!.value,
      siegeSocial: this.editProprioForm.get(['siegeSocial'])!.value,
      personneMorale: this.editProprioForm.get(['personneMorale'])!.value,
      dateNaiss: this.editProprioForm.get(['dateNaiss'])!.value
        ? dayjs(this.editProprioForm.get(['dateNaiss'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lieuNaissance: this.editProprioForm.get(['lieuNaissance'])!.value,
      numCNI: this.editProprioForm.get(['numCNI'])!.value,
      ninea: this.editProprioForm.get(['ninea'])!.value,
      adresse: this.editProprioForm.get(['adresse'])!.value,
      email: this.editProprioForm.get(['email'])!.value,
      telephone: this.editProprioForm.get(['telephone'])!.value,
      telephone2: this.editProprioForm.get(['telephone2'])!.value,
      telephone3: this.editProprioForm.get(['telephone3'])!.value,
      aquisition: this.editProprioForm.get(['aquisition'])!.value,
      statutPersoneStructure: this.editProprioForm.get(['statutPersoneStructure'])!.value,
      typeStructure: this.editProprioForm.get(['typeStructure'])!.value,
      nombreHeritiers: this.editProprioForm.get(['nombreHeritiers'])!.value,
      serviceOcupant: this.editProprioForm.get(['serviceOcupant'])!.value,
      etablssement: this.editProprioForm.get(['etablssement'])!.value,
    };
  }
  protected updateRepForm(representant: IRepresentant): void {
    this.editRepForm.patchValue({
      id: representant.id,
      prenom: representant.prenom,
      lienProprietaire: representant.lienProprietaire,
      nom: representant.nom,
      actif: representant.actif,
      raisonSocial: representant.raisonSocial,
      siegeSocial: representant.siegeSocial,
      personneMorale: representant.personneMorale,
      dateNaiss: representant.dateNaiss ? representant.dateNaiss.format(DATE_TIME_FORMAT) : null,
      lieuNaissance: representant.lieuNaissance,
      numCNI: representant.numCNI,
      ninea: representant.ninea,
      adresse: representant.adresse,
      email: representant.email,
      telephone: representant.telephone,
      telephone2: representant.telephone2,
      telephone3: representant.telephone3,
      statutPersoneStructure: representant.statutPersoneStructure,
      typeStructure: representant.typeStructure,
      proprietaire: representant.proprietaire,
    });
  }

  protected createRepFromForm(): IRepresentant {
    return {
      ...new Representant(),
      id: this.editRepForm.get(['id'])!.value,
      prenom: this.editRepForm.get(['prenom'])!.value,
      lienProprietaire: this.editRepForm.get(['lienProprietaire'])!.value,
      nom: this.editRepForm.get(['nom'])!.value,
      actif: this.editRepForm.get(['actif'])!.value,
      raisonSocial: this.editRepForm.get(['raisonSocial'])!.value,
      siegeSocial: this.editRepForm.get(['siegeSocial'])!.value,
      personneMorale: this.editRepForm.get(['personneMorale'])!.value,
      dateNaiss: this.editRepForm.get(['dateNaiss'])!.value
        ? dayjs(this.editRepForm.get(['dateNaiss'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lieuNaissance: this.editRepForm.get(['lieuNaissance'])!.value,
      numCNI: this.editRepForm.get(['numCNI'])!.value,
      ninea: this.editRepForm.get(['ninea'])!.value,
      adresse: this.editRepForm.get(['adresse'])!.value,
      email: this.editRepForm.get(['email'])!.value,
      telephone: this.editRepForm.get(['telephone'])!.value,
      telephone2: this.editRepForm.get(['telephone2'])!.value,
      telephone3: this.editRepForm.get(['telephone3'])!.value,
      statutPersoneStructure: this.editRepForm.get(['statutPersoneStructure'])!.value,
      typeStructure: this.editRepForm.get(['typeStructure'])!.value,
      proprietaire: this.editRepForm.get(['proprietaire'])!.value,
    };
  }
}
