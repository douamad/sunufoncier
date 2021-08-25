jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EvaluationBatimentsService } from '../service/evaluation-batiments.service';
import { IEvaluationBatiments, EvaluationBatiments } from '../evaluation-batiments.model';
import { ICategorieNature } from 'app/entities/categorie-nature/categorie-nature.model';
import { CategorieNatureService } from 'app/entities/categorie-nature/service/categorie-nature.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

import { EvaluationBatimentsUpdateComponent } from './evaluation-batiments-update.component';

describe('Component Tests', () => {
  describe('EvaluationBatiments Management Update Component', () => {
    let comp: EvaluationBatimentsUpdateComponent;
    let fixture: ComponentFixture<EvaluationBatimentsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let evaluationBatimentsService: EvaluationBatimentsService;
    let categorieNatureService: CategorieNatureService;
    let dossierService: DossierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EvaluationBatimentsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EvaluationBatimentsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EvaluationBatimentsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      evaluationBatimentsService = TestBed.inject(EvaluationBatimentsService);
      categorieNatureService = TestBed.inject(CategorieNatureService);
      dossierService = TestBed.inject(DossierService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call CategorieNature query and add missing value', () => {
        const evaluationBatiments: IEvaluationBatiments = { id: 456 };
        const categorieNature: ICategorieNature = { id: 52430 };
        evaluationBatiments.categorieNature = categorieNature;

        const categorieNatureCollection: ICategorieNature[] = [{ id: 25005 }];
        spyOn(categorieNatureService, 'query').and.returnValue(of(new HttpResponse({ body: categorieNatureCollection })));
        const additionalCategorieNatures = [categorieNature];
        const expectedCollection: ICategorieNature[] = [...additionalCategorieNatures, ...categorieNatureCollection];
        spyOn(categorieNatureService, 'addCategorieNatureToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ evaluationBatiments });
        comp.ngOnInit();

        expect(categorieNatureService.query).toHaveBeenCalled();
        expect(categorieNatureService.addCategorieNatureToCollectionIfMissing).toHaveBeenCalledWith(
          categorieNatureCollection,
          ...additionalCategorieNatures
        );
        expect(comp.categorieNaturesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Dossier query and add missing value', () => {
        const evaluationBatiments: IEvaluationBatiments = { id: 456 };
        const dossier: IDossier = { id: 56665 };
        evaluationBatiments.dossier = dossier;

        const dossierCollection: IDossier[] = [{ id: 48427 }];
        spyOn(dossierService, 'query').and.returnValue(of(new HttpResponse({ body: dossierCollection })));
        const additionalDossiers = [dossier];
        const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
        spyOn(dossierService, 'addDossierToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ evaluationBatiments });
        comp.ngOnInit();

        expect(dossierService.query).toHaveBeenCalled();
        expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, ...additionalDossiers);
        expect(comp.dossiersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const evaluationBatiments: IEvaluationBatiments = { id: 456 };
        const categorieNature: ICategorieNature = { id: 16620 };
        evaluationBatiments.categorieNature = categorieNature;
        const dossier: IDossier = { id: 26637 };
        evaluationBatiments.dossier = dossier;

        activatedRoute.data = of({ evaluationBatiments });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(evaluationBatiments));
        expect(comp.categorieNaturesSharedCollection).toContain(categorieNature);
        expect(comp.dossiersSharedCollection).toContain(dossier);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationBatiments = { id: 123 };
        spyOn(evaluationBatimentsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationBatiments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: evaluationBatiments }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(evaluationBatimentsService.update).toHaveBeenCalledWith(evaluationBatiments);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationBatiments = new EvaluationBatiments();
        spyOn(evaluationBatimentsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationBatiments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: evaluationBatiments }));
        saveSubject.complete();

        // THEN
        expect(evaluationBatimentsService.create).toHaveBeenCalledWith(evaluationBatiments);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationBatiments = { id: 123 };
        spyOn(evaluationBatimentsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationBatiments });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(evaluationBatimentsService.update).toHaveBeenCalledWith(evaluationBatiments);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCategorieNatureById', () => {
        it('Should return tracked CategorieNature primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategorieNatureById(0, entity);
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
