jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LocataireService } from '../service/locataire.service';
import { ILocataire, Locataire } from '../locataire.model';
import { IDossier } from 'app/entities/dossier/dossier.model';
import { DossierService } from 'app/entities/dossier/service/dossier.service';

import { LocataireUpdateComponent } from './locataire-update.component';

describe('Component Tests', () => {
  describe('Locataire Management Update Component', () => {
    let comp: LocataireUpdateComponent;
    let fixture: ComponentFixture<LocataireUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let locataireService: LocataireService;
    let dossierService: DossierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LocataireUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(LocataireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LocataireUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      locataireService = TestBed.inject(LocataireService);
      dossierService = TestBed.inject(DossierService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Dossier query and add missing value', () => {
        const locataire: ILocataire = { id: 456 };
        const dossier: IDossier = { id: 12149 };
        locataire.dossier = dossier;

        const dossierCollection: IDossier[] = [{ id: 53346 }];
        spyOn(dossierService, 'query').and.returnValue(of(new HttpResponse({ body: dossierCollection })));
        const additionalDossiers = [dossier];
        const expectedCollection: IDossier[] = [...additionalDossiers, ...dossierCollection];
        spyOn(dossierService, 'addDossierToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ locataire });
        comp.ngOnInit();

        expect(dossierService.query).toHaveBeenCalled();
        expect(dossierService.addDossierToCollectionIfMissing).toHaveBeenCalledWith(dossierCollection, ...additionalDossiers);
        expect(comp.dossiersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const locataire: ILocataire = { id: 456 };
        const dossier: IDossier = { id: 25898 };
        locataire.dossier = dossier;

        activatedRoute.data = of({ locataire });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(locataire));
        expect(comp.dossiersSharedCollection).toContain(dossier);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const locataire = { id: 123 };
        spyOn(locataireService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ locataire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: locataire }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(locataireService.update).toHaveBeenCalledWith(locataire);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const locataire = new Locataire();
        spyOn(locataireService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ locataire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: locataire }));
        saveSubject.complete();

        // THEN
        expect(locataireService.create).toHaveBeenCalledWith(locataire);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const locataire = { id: 123 };
        spyOn(locataireService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ locataire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(locataireService.update).toHaveBeenCalledWith(locataire);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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
