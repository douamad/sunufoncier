jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RepresentantService } from '../service/representant.service';
import { IRepresentant, Representant } from '../representant.model';
import { IProprietaire } from 'app/entities/proprietaire/proprietaire.model';
import { ProprietaireService } from 'app/entities/proprietaire/service/proprietaire.service';

import { RepresentantUpdateComponent } from './representant-update.component';

describe('Component Tests', () => {
  describe('Representant Management Update Component', () => {
    let comp: RepresentantUpdateComponent;
    let fixture: ComponentFixture<RepresentantUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let representantService: RepresentantService;
    let proprietaireService: ProprietaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RepresentantUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RepresentantUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RepresentantUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      representantService = TestBed.inject(RepresentantService);
      proprietaireService = TestBed.inject(ProprietaireService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Proprietaire query and add missing value', () => {
        const representant: IRepresentant = { id: 456 };
        const proprietaire: IProprietaire = { id: 9021 };
        representant.proprietaire = proprietaire;

        const proprietaireCollection: IProprietaire[] = [{ id: 94108 }];
        spyOn(proprietaireService, 'query').and.returnValue(of(new HttpResponse({ body: proprietaireCollection })));
        const additionalProprietaires = [proprietaire];
        const expectedCollection: IProprietaire[] = [...additionalProprietaires, ...proprietaireCollection];
        spyOn(proprietaireService, 'addProprietaireToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ representant });
        comp.ngOnInit();

        expect(proprietaireService.query).toHaveBeenCalled();
        expect(proprietaireService.addProprietaireToCollectionIfMissing).toHaveBeenCalledWith(
          proprietaireCollection,
          ...additionalProprietaires
        );
        expect(comp.proprietairesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const representant: IRepresentant = { id: 456 };
        const proprietaire: IProprietaire = { id: 64281 };
        representant.proprietaire = proprietaire;

        activatedRoute.data = of({ representant });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(representant));
        expect(comp.proprietairesSharedCollection).toContain(proprietaire);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const representant = { id: 123 };
        spyOn(representantService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ representant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: representant }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(representantService.update).toHaveBeenCalledWith(representant);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const representant = new Representant();
        spyOn(representantService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ representant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: representant }));
        saveSubject.complete();

        // THEN
        expect(representantService.create).toHaveBeenCalledWith(representant);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const representant = { id: 123 };
        spyOn(representantService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ representant });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(representantService.update).toHaveBeenCalledWith(representant);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackProprietaireById', () => {
        it('Should return tracked Proprietaire primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProprietaireById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
