jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RefParcelaireService } from '../service/ref-parcelaire.service';
import { IRefParcelaire, RefParcelaire } from '../ref-parcelaire.model';
import { ICommune } from 'app/entities/commune/commune.model';
import { CommuneService } from 'app/entities/commune/service/commune.service';

import { RefParcelaireUpdateComponent } from './ref-parcelaire-update.component';

describe('Component Tests', () => {
  describe('RefParcelaire Management Update Component', () => {
    let comp: RefParcelaireUpdateComponent;
    let fixture: ComponentFixture<RefParcelaireUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let refParcelaireService: RefParcelaireService;
    let communeService: CommuneService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RefParcelaireUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RefParcelaireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RefParcelaireUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      refParcelaireService = TestBed.inject(RefParcelaireService);
      communeService = TestBed.inject(CommuneService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Commune query and add missing value', () => {
        const refParcelaire: IRefParcelaire = { id: 456 };
        const commune: ICommune = { id: 20572 };
        refParcelaire.commune = commune;

        const communeCollection: ICommune[] = [{ id: 13176 }];
        spyOn(communeService, 'query').and.returnValue(of(new HttpResponse({ body: communeCollection })));
        const additionalCommunes = [commune];
        const expectedCollection: ICommune[] = [...additionalCommunes, ...communeCollection];
        spyOn(communeService, 'addCommuneToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ refParcelaire });
        comp.ngOnInit();

        expect(communeService.query).toHaveBeenCalled();
        expect(communeService.addCommuneToCollectionIfMissing).toHaveBeenCalledWith(communeCollection, ...additionalCommunes);
        expect(comp.communesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const refParcelaire: IRefParcelaire = { id: 456 };
        const commune: ICommune = { id: 90486 };
        refParcelaire.commune = commune;

        activatedRoute.data = of({ refParcelaire });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(refParcelaire));
        expect(comp.communesSharedCollection).toContain(commune);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const refParcelaire = { id: 123 };
        spyOn(refParcelaireService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ refParcelaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: refParcelaire }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(refParcelaireService.update).toHaveBeenCalledWith(refParcelaire);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const refParcelaire = new RefParcelaire();
        spyOn(refParcelaireService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ refParcelaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: refParcelaire }));
        saveSubject.complete();

        // THEN
        expect(refParcelaireService.create).toHaveBeenCalledWith(refParcelaire);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const refParcelaire = { id: 123 };
        spyOn(refParcelaireService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ refParcelaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(refParcelaireService.update).toHaveBeenCalledWith(refParcelaire);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCommuneById', () => {
        it('Should return tracked Commune primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCommuneById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
