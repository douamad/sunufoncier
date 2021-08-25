jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DossierService } from '../service/dossier.service';
import { IDossier, Dossier } from '../dossier.model';
import { ILotissement } from 'app/entities/lotissement/lotissement.model';
import { LotissementService } from 'app/entities/lotissement/service/lotissement.service';
import { IUsageDossier } from 'app/entities/usage-dossier/usage-dossier.model';
import { UsageDossierService } from 'app/entities/usage-dossier/service/usage-dossier.service';
import { IProprietaire } from 'app/entities/proprietaire/proprietaire.model';
import { ProprietaireService } from 'app/entities/proprietaire/service/proprietaire.service';
import { IRefParcelaire } from 'app/entities/ref-parcelaire/ref-parcelaire.model';
import { RefParcelaireService } from 'app/entities/ref-parcelaire/service/ref-parcelaire.service';
import { IRefcadastrale } from 'app/entities/refcadastrale/refcadastrale.model';
import { RefcadastraleService } from 'app/entities/refcadastrale/service/refcadastrale.service';

import { DossierUpdateComponent } from './dossier-update.component';

describe('Component Tests', () => {
  describe('Dossier Management Update Component', () => {
    let comp: DossierUpdateComponent;
    let fixture: ComponentFixture<DossierUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dossierService: DossierService;
    let lotissementService: LotissementService;
    let usageDossierService: UsageDossierService;
    let proprietaireService: ProprietaireService;
    let refParcelaireService: RefParcelaireService;
    let refcadastraleService: RefcadastraleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DossierUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DossierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DossierUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dossierService = TestBed.inject(DossierService);
      lotissementService = TestBed.inject(LotissementService);
      usageDossierService = TestBed.inject(UsageDossierService);
      proprietaireService = TestBed.inject(ProprietaireService);
      refParcelaireService = TestBed.inject(RefParcelaireService);
      refcadastraleService = TestBed.inject(RefcadastraleService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Lotissement query and add missing value', () => {
        const dossier: IDossier = { id: 456 };
        const dossier: ILotissement = { id: 13302 };
        dossier.dossier = dossier;

        const lotissementCollection: ILotissement[] = [{ id: 49750 }];
        spyOn(lotissementService, 'query').and.returnValue(of(new HttpResponse({ body: lotissementCollection })));
        const additionalLotissements = [dossier];
        const expectedCollection: ILotissement[] = [...additionalLotissements, ...lotissementCollection];
        spyOn(lotissementService, 'addLotissementToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        expect(lotissementService.query).toHaveBeenCalled();
        expect(lotissementService.addLotissementToCollectionIfMissing).toHaveBeenCalledWith(
          lotissementCollection,
          ...additionalLotissements
        );
        expect(comp.lotissementsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call UsageDossier query and add missing value', () => {
        const dossier: IDossier = { id: 456 };
        const usageDossier: IUsageDossier = { id: 78573 };
        dossier.usageDossier = usageDossier;

        const usageDossierCollection: IUsageDossier[] = [{ id: 54060 }];
        spyOn(usageDossierService, 'query').and.returnValue(of(new HttpResponse({ body: usageDossierCollection })));
        const additionalUsageDossiers = [usageDossier];
        const expectedCollection: IUsageDossier[] = [...additionalUsageDossiers, ...usageDossierCollection];
        spyOn(usageDossierService, 'addUsageDossierToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        expect(usageDossierService.query).toHaveBeenCalled();
        expect(usageDossierService.addUsageDossierToCollectionIfMissing).toHaveBeenCalledWith(
          usageDossierCollection,
          ...additionalUsageDossiers
        );
        expect(comp.usageDossiersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Proprietaire query and add missing value', () => {
        const dossier: IDossier = { id: 456 };
        const proprietaire: IProprietaire = { id: 59959 };
        dossier.proprietaire = proprietaire;

        const proprietaireCollection: IProprietaire[] = [{ id: 69697 }];
        spyOn(proprietaireService, 'query').and.returnValue(of(new HttpResponse({ body: proprietaireCollection })));
        const additionalProprietaires = [proprietaire];
        const expectedCollection: IProprietaire[] = [...additionalProprietaires, ...proprietaireCollection];
        spyOn(proprietaireService, 'addProprietaireToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        expect(proprietaireService.query).toHaveBeenCalled();
        expect(proprietaireService.addProprietaireToCollectionIfMissing).toHaveBeenCalledWith(
          proprietaireCollection,
          ...additionalProprietaires
        );
        expect(comp.proprietairesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call RefParcelaire query and add missing value', () => {
        const dossier: IDossier = { id: 456 };
        const refParcelaire: IRefParcelaire = { id: 1344 };
        dossier.refParcelaire = refParcelaire;

        const refParcelaireCollection: IRefParcelaire[] = [{ id: 9572 }];
        spyOn(refParcelaireService, 'query').and.returnValue(of(new HttpResponse({ body: refParcelaireCollection })));
        const additionalRefParcelaires = [refParcelaire];
        const expectedCollection: IRefParcelaire[] = [...additionalRefParcelaires, ...refParcelaireCollection];
        spyOn(refParcelaireService, 'addRefParcelaireToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        expect(refParcelaireService.query).toHaveBeenCalled();
        expect(refParcelaireService.addRefParcelaireToCollectionIfMissing).toHaveBeenCalledWith(
          refParcelaireCollection,
          ...additionalRefParcelaires
        );
        expect(comp.refParcelairesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Refcadastrale query and add missing value', () => {
        const dossier: IDossier = { id: 456 };
        const refcadastrale: IRefcadastrale = { id: 62427 };
        dossier.refcadastrale = refcadastrale;

        const refcadastraleCollection: IRefcadastrale[] = [{ id: 69163 }];
        spyOn(refcadastraleService, 'query').and.returnValue(of(new HttpResponse({ body: refcadastraleCollection })));
        const additionalRefcadastrales = [refcadastrale];
        const expectedCollection: IRefcadastrale[] = [...additionalRefcadastrales, ...refcadastraleCollection];
        spyOn(refcadastraleService, 'addRefcadastraleToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        expect(refcadastraleService.query).toHaveBeenCalled();
        expect(refcadastraleService.addRefcadastraleToCollectionIfMissing).toHaveBeenCalledWith(
          refcadastraleCollection,
          ...additionalRefcadastrales
        );
        expect(comp.refcadastralesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const dossier: IDossier = { id: 456 };
        const dossier: ILotissement = { id: 80696 };
        dossier.dossier = dossier;
        const usageDossier: IUsageDossier = { id: 71550 };
        dossier.usageDossier = usageDossier;
        const proprietaire: IProprietaire = { id: 63896 };
        dossier.proprietaire = proprietaire;
        const refParcelaire: IRefParcelaire = { id: 7509 };
        dossier.refParcelaire = refParcelaire;
        const refcadastrale: IRefcadastrale = { id: 37203 };
        dossier.refcadastrale = refcadastrale;

        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dossier));
        expect(comp.lotissementsSharedCollection).toContain(dossier);
        expect(comp.usageDossiersSharedCollection).toContain(usageDossier);
        expect(comp.proprietairesSharedCollection).toContain(proprietaire);
        expect(comp.refParcelairesSharedCollection).toContain(refParcelaire);
        expect(comp.refcadastralesSharedCollection).toContain(refcadastrale);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dossier = { id: 123 };
        spyOn(dossierService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dossier }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dossierService.update).toHaveBeenCalledWith(dossier);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dossier = new Dossier();
        spyOn(dossierService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dossier }));
        saveSubject.complete();

        // THEN
        expect(dossierService.create).toHaveBeenCalledWith(dossier);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dossier = { id: 123 };
        spyOn(dossierService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dossier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dossierService.update).toHaveBeenCalledWith(dossier);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackLotissementById', () => {
        it('Should return tracked Lotissement primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackLotissementById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackUsageDossierById', () => {
        it('Should return tracked UsageDossier primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUsageDossierById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackProprietaireById', () => {
        it('Should return tracked Proprietaire primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProprietaireById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackRefParcelaireById', () => {
        it('Should return tracked RefParcelaire primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRefParcelaireById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackRefcadastraleById', () => {
        it('Should return tracked Refcadastrale primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRefcadastraleById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
