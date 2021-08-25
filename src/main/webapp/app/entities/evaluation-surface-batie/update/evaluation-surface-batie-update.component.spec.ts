jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EvaluationSurfaceBatieService } from '../service/evaluation-surface-batie.service';
import { IEvaluationSurfaceBatie, EvaluationSurfaceBatie } from '../evaluation-surface-batie.model';
import { ICategorieBatie } from 'app/entities/categorie-batie/categorie-batie.model';
import { CategorieBatieService } from 'app/entities/categorie-batie/service/categorie-batie.service';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

import { EvaluationSurfaceBatieUpdateComponent } from './evaluation-surface-batie-update.component';

describe('Component Tests', () => {
  describe('EvaluationSurfaceBatie Management Update Component', () => {
    let comp: EvaluationSurfaceBatieUpdateComponent;
    let fixture: ComponentFixture<EvaluationSurfaceBatieUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let evaluationSurfaceBatieService: EvaluationSurfaceBatieService;
    let categorieBatieService: CategorieBatieService;
    let dossierService: DossierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EvaluationSurfaceBatieUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EvaluationSurfaceBatieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EvaluationSurfaceBatieUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      evaluationSurfaceBatieService = TestBed.inject(EvaluationSurfaceBatieService);
      categorieBatieService = TestBed.inject(CategorieBatieService);
      dossierService = TestBed.inject(DossierService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call CategorieBatie query and add missing value', () => {
        const evaluationSurfaceBatie: IEvaluationSurfaceBatie = { id: 456 };
        const categorieBatie: ICategorieBatie = { id: 69177 };
        evaluationSurfaceBatie.categorieBatie = categorieBatie;

        const categorieBatieCollection: ICategorieBatie[] = [{ id: 74655 }];
        spyOn(categorieBatieService, 'query').and.returnValue(of(new HttpResponse({ body: categorieBatieCollection })));
        const additionalCategorieBaties = [categorieBatie];
        const expectedCollection: ICategorieBatie[] = [...additionalCategorieBaties, ...categorieBatieCollection];
        spyOn(categorieBatieService, 'addCategorieBatieToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ evaluationSurfaceBatie });
        comp.ngOnInit();

        expect(categorieBatieService.query).toHaveBeenCalled();
        expect(categorieBatieService.addCategorieBatieToCollectionIfMissing).toHaveBeenCalledWith(
          categorieBatieCollection,
          ...additionalCategorieBaties
        );
        expect(comp.categorieBatiesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Dossier query and add missing value', () => {
        const evaluationSurfaceBatie: IEvaluationSurfaceBatie = { id: 456 };
        const dossier: IDossier = { id: 97578 };
        evaluationSurfaceBatie.dossier = dossier;

        const dossierCollection: IDossier[] = [{ id: 73673 }];
        spyOn(dossierService, 'query').and.returnValue(of(new HttpResponse({ body: dossierCollection })));
        const additionalDossiers = [dossier];
        const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
        spyOn(dossierService, 'addDossierToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ evaluationSurfaceBatie });
        comp.ngOnInit();

        expect(dossierService.query).toHaveBeenCalled();
        expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, ...additionalDossiers);
        expect(comp.dossiersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const evaluationSurfaceBatie: IEvaluationSurfaceBatie = { id: 456 };
        const categorieBatie: ICategorieBatie = { id: 75373 };
        evaluationSurfaceBatie.categorieBatie = categorieBatie;
        const dossier: IDossier = { id: 65423 };
        evaluationSurfaceBatie.dossier = dossier;

        activatedRoute.data = of({ evaluationSurfaceBatie });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(evaluationSurfaceBatie));
        expect(comp.categorieBatiesSharedCollection).toContain(categorieBatie);
        expect(comp.dossiersSharedCollection).toContain(dossier);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationSurfaceBatie = { id: 123 };
        spyOn(evaluationSurfaceBatieService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationSurfaceBatie });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: evaluationSurfaceBatie }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(evaluationSurfaceBatieService.update).toHaveBeenCalledWith(evaluationSurfaceBatie);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationSurfaceBatie = new EvaluationSurfaceBatie();
        spyOn(evaluationSurfaceBatieService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationSurfaceBatie });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: evaluationSurfaceBatie }));
        saveSubject.complete();

        // THEN
        expect(evaluationSurfaceBatieService.create).toHaveBeenCalledWith(evaluationSurfaceBatie);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const evaluationSurfaceBatie = { id: 123 };
        spyOn(evaluationSurfaceBatieService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ evaluationSurfaceBatie });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(evaluationSurfaceBatieService.update).toHaveBeenCalledWith(evaluationSurfaceBatie);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCategorieBatieById', () => {
        it('Should return tracked CategorieBatie primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCategorieBatieById(0, entity);
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
