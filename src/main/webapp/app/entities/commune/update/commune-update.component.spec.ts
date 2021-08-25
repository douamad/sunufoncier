jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CommuneService } from '../service/commune.service';
import { ICommune, Commune } from '../commune.model';
import { IArrondissement } from 'app/entities/arrondissement/arrondissement.model';
import { ArrondissementService } from 'app/entities/arrondissement/service/arrondissement.service';

import { CommuneUpdateComponent } from './commune-update.component';

describe('Component Tests', () => {
  describe('Commune Management Update Component', () => {
    let comp: CommuneUpdateComponent;
    let fixture: ComponentFixture<CommuneUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let communeService: CommuneService;
    let arrondissementService: ArrondissementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CommuneUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CommuneUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommuneUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      communeService = TestBed.inject(CommuneService);
      arrondissementService = TestBed.inject(ArrondissementService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Arrondissement query and add missing value', () => {
        const commune: ICommune = { id: 456 };
        const arrondissement: IArrondissement = { id: 29304 };
        commune.arrondissement = arrondissement;

        const arrondissementCollection: IArrondissement[] = [{ id: 43950 }];
        spyOn(arrondissementService, 'query').and.returnValue(of(new HttpResponse({ body: arrondissementCollection })));
        const additionalArrondissements = [arrondissement];
        const expectedCollection: IArrondissement[] = [...additionalArrondissements, ...arrondissementCollection];
        spyOn(arrondissementService, 'addArrondissementToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ commune });
        comp.ngOnInit();

        expect(arrondissementService.query).toHaveBeenCalled();
        expect(arrondissementService.addArrondissementToCollectionIfMissing).toHaveBeenCalledWith(
          arrondissementCollection,
          ...additionalArrondissements
        );
        expect(comp.arrondissementsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const commune: ICommune = { id: 456 };
        const arrondissement: IArrondissement = { id: 30530 };
        commune.arrondissement = arrondissement;

        activatedRoute.data = of({ commune });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(commune));
        expect(comp.arrondissementsSharedCollection).toContain(arrondissement);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const commune = { id: 123 };
        spyOn(communeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ commune });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: commune }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(communeService.update).toHaveBeenCalledWith(commune);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const commune = new Commune();
        spyOn(communeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ commune });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: commune }));
        saveSubject.complete();

        // THEN
        expect(communeService.create).toHaveBeenCalledWith(commune);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const commune = { id: 123 };
        spyOn(communeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ commune });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(communeService.update).toHaveBeenCalledWith(commune);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackArrondissementById', () => {
        it('Should return tracked Arrondissement primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackArrondissementById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
