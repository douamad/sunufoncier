jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EvaluationCoursAmenageService } from '../service/evaluation-cours-amenage.service';
import { IEvaluationCoursAmenage, EvaluationCoursAmenage } from '../evaluation-cours-amenage.model';
import { ICategorieCoursAmenage } from 'app/entities/categorie-cours-amenage/categorie-cours-amenage.model';
import { CategorieCoursAmenageService } from 'app/entities/categorie-cours-amenage/service/categorie-cours-amenage.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

import { EvaluationCoursAmenageUpdateComponent } from './evaluation-cours-amenage-update.component';

describe('Component Tests', () => {
  describe('EvaluationCoursAmenage Management Update Component', () => {
    let comp: EvaluationCoursAmenageUpdateComponent;
    let fixture: ComponentFixture<EvaluationCoursAmenageUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let evaluationCoursAmenageService: EvaluationCoursAmenageService;
    let categorieCoursAmenageService: CategorieCoursAmenageService;
    let dossierService: DossierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EvaluationCoursAmenageUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EvaluationCoursAmenageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EvaluationCoursAmenageUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      evaluationCoursAmenageService = TestBed.inject(EvaluationCoursAmenageService);
      categorieCoursAmenageService = TestBed.inject(CategorieCoursAmenageService);
      dossierService = TestBed.inject(DossierService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call CategorieCoursAmenage query and add missing value', () => {
        const evaluationCoursAmenage: IEvaluationCoursAmenage = { id: 456 };
        const categorieCoursAmenage: ICategorieCoursAmenage = { id: 38291 };
        evaluationCoursAmenage.categorieCoursAmenage = categorieCoursAmenage;

        const categorieCoursAmenageCollection: ICategorieCoursAmenage[] = [{ id: 36847 }];
        spyOn(categorieCoursAmenageService, 'query').and.returnValue(of(new HttpResponse({ body: categorieCoursAmenageCollection })));
        const additionalCategorieCoursAmenages = [categorieCoursAmenage];
        const expectedCollection: ICategorieCoursAmenage[] = [...additionalCategorieCoursAmenages, ...categorieCoursAmenageCollection];
        spyOn(categorieCoursAmenageService, 'addCategorieCoursAmenageToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ evaluationCoursAmenage });
        comp.ngOnInit();

        expect(categorieCoursAmenageService.query).toHaveBeenCalled();
        expect(categorieCoursAmenageService.addCategorieCoursAmenageToCollectionIfMissing).toHaveBeenCalledWith(
          categorieCoursAmenageCollection,
          ...additionalCategorieCoursAmenages
        );
        expect(comp.categorieCoursAmenagesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Dossier query and add missing value', () => {
        const evaluationCoursAmenage: IEvaluationCoursAmenage = { id: 456 };
        const dossier: IDossier = { id: 259 };
        evaluationCoursAmenage.dossier = dossier;

        const dossierCollection: IDossier[] = [{ id: 29695 }];
        spyOn(dossierService, 'query').and.returnValue(of(new HttpResponse({ body: dossierCollection })));
        const additionalDossiers = [dossier];
        const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
        spyOn(dossierService, 'addDossierToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ evaluationCoursAmenage });
        comp.ngOnInit();

        expect(dossierService.query).toHaveBeenCalled();
        expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, ...additionalDossiers);
        expect(comp.dossiersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const evaluationCoursAmenage: IEvaluationCoursAmenage = { id: 456 };
        const categorieCoursAmenage: ICategorieCoursAmenage = { id: 79324 };
        evaluationCoursAmenage.categorieCoursAmenage = categorieCoursAmenage;
        const dossier: IDossier = { id: 73479 };
        evaluationCoursAmenage.dossier = dossier;

        activatedRoute.data = of({ evaluationCoursAmenage });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(evaluationCoursAmenage));
        expect(comp.categorieCoursAmenagesSharedCollection).toContain(categorieCoursAmenage);
        expect(comp.dossiersSharedCollection).toContain(dossier);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationCoursAmenage = { id: 123 };
        spyOn(evaluationCoursAmenageService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationCoursAmenage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: evaluationCoursAmenage }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(evaluationCoursAmenageService.update).toHaveBeenCalledWith(evaluationCoursAmenage);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationCoursAmenage = new EvaluationCoursAmenage();
        spyOn(evaluationCoursAmenageService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationCoursAmenage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: evaluationCoursAmenage }));
        saveSubject.complete();

        // THEN
        expect(evaluationCoursAmenageService.create).toHaveBeenCalledWith(evaluationCoursAmenage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationCoursAmenage = { id: 123 };
        spyOn(evaluationCoursAmenageService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationCoursAmenage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(evaluationCoursAmenageService.update).toHaveBeenCalledWith(evaluationCoursAmenage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCategorieCoursAmenageById', () => {
        it('Should return tracked CategorieCoursAmenage primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategorieCoursAmenageById(0, entity);
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
