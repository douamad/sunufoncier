jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EvaluationClotureService } from '../service/evaluation-cloture.service';
import { IEvaluationCloture, EvaluationCloture } from '../evaluation-cloture.model';
import { ICategorieCloture } from 'app/entities/categorie-cloture/categorie-cloture.model';
import { CategorieClotureService } from 'app/entities/categorie-cloture/service/categorie-cloture.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

import { EvaluationClotureUpdateComponent } from './evaluation-cloture-update.component';

describe('Component Tests', () => {
  describe('EvaluationCloture Management Update Component', () => {
    let comp: EvaluationClotureUpdateComponent;
    let fixture: ComponentFixture<EvaluationClotureUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let evaluationClotureService: EvaluationClotureService;
    let categorieClotureService: CategorieClotureService;
    let dossierService: DossierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EvaluationClotureUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EvaluationClotureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EvaluationClotureUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      evaluationClotureService = TestBed.inject(EvaluationClotureService);
      categorieClotureService = TestBed.inject(CategorieClotureService);
      dossierService = TestBed.inject(DossierService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call CategorieCloture query and add missing value', () => {
        const evaluationCloture: IEvaluationCloture = { id: 456 };
        const categoriteCloture: ICategorieCloture = { id: 95294 };
        evaluationCloture.categoriteCloture = categoriteCloture;

        const categorieClotureCollection: ICategorieCloture[] = [{ id: 99016 }];
        spyOn(categorieClotureService, 'query').and.returnValue(of(new HttpResponse({ body: categorieClotureCollection })));
        const additionalCategorieClotures = [categoriteCloture];
        const expectedCollection: ICategorieCloture[] = [...additionalCategorieClotures, ...categorieClotureCollection];
        spyOn(categorieClotureService, 'addCategorieClotureToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ evaluationCloture });
        comp.ngOnInit();

        expect(categorieClotureService.query).toHaveBeenCalled();
        expect(categorieClotureService.addCategorieClotureToCollectionIfMissing).toHaveBeenCalledWith(
          categorieClotureCollection,
          ...additionalCategorieClotures
        );
        expect(comp.categorieCloturesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Dossier query and add missing value', () => {
        const evaluationCloture: IEvaluationCloture = { id: 456 };
        const dossier: IDossier = { id: 43508 };
        evaluationCloture.dossier = dossier;

        const dossierCollection: IDossier[] = [{ id: 75193 }];
        spyOn(dossierService, 'query').and.returnValue(of(new HttpResponse({ body: dossierCollection })));
        const additionalDossiers = [dossier];
        const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
        spyOn(dossierService, 'addDossierToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ evaluationCloture });
        comp.ngOnInit();

        expect(dossierService.query).toHaveBeenCalled();
        expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, ...additionalDossiers);
        expect(comp.dossiersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const evaluationCloture: IEvaluationCloture = { id: 456 };
        const categoriteCloture: ICategorieCloture = { id: 25608 };
        evaluationCloture.categoriteCloture = categoriteCloture;
        const dossier: IDossier = { id: 26562 };
        evaluationCloture.dossier = dossier;

        activatedRoute.data = of({ evaluationCloture });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(evaluationCloture));
        expect(comp.categorieCloturesSharedCollection).toContain(categoriteCloture);
        expect(comp.dossiersSharedCollection).toContain(dossier);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationCloture = { id: 123 };
        spyOn(evaluationClotureService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationCloture });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: evaluationCloture }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(evaluationClotureService.update).toHaveBeenCalledWith(evaluationCloture);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationCloture = new EvaluationCloture();
        spyOn(evaluationClotureService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationCloture });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: evaluationCloture }));
        saveSubject.complete();

        // THEN
        expect(evaluationClotureService.create).toHaveBeenCalledWith(evaluationCloture);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationCloture = { id: 123 };
        spyOn(evaluationClotureService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationCloture });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(evaluationClotureService.update).toHaveBeenCalledWith(evaluationCloture);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCategorieClotureById', () => {
        it('Should return tracked CategorieCloture primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategorieClotureById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackDossierById', () => {
        it('Should return tracked Dossier primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDossierById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
