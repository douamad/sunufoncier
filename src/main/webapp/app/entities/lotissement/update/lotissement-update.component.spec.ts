jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LotissementService } from '../service/lotissement.service';
import { ILotissement, Lotissement } from '../lotissement.model';
import { IQuartier } from 'app/entities/quartier/quartier.model';
import { QuartierService } from 'app/entities/quartier/service/quartier.service';

import { LotissementUpdateComponent } from './lotissement-update.component';

describe('Component Tests', () => {
  describe('Lotissement Management Update Component', () => {
    let comp: LotissementUpdateComponent;
    let fixture: ComponentFixture<LotissementUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let lotissementService: LotissementService;
    let quartierService: QuartierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [LotissementUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(LotissementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LotissementUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      lotissementService = TestBed.inject(LotissementService);
      quartierService = TestBed.inject(QuartierService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Quartier query and add missing value', () => {
        const lotissement: ILotissement = { id: 456 };
        const quartier: IQuartier = { id: 16079 };
        lotissement.quartier = quartier;

        const quartierCollection: IQuartier[] = [{ id: 80942 }];
        spyOn(quartierService, 'query').and.returnValue(of(new HttpResponse({ body: quartierCollection })));
        const additionalQuartiers = [quartier];
        const expectedCollection: IQuartier[] = [...additionalQuartiers, ...quartierCollection];
        spyOn(quartierService, 'addQuartierToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ lotissement });
        comp.ngOnInit();

        expect(quartierService.query).toHaveBeenCalled();
        expect(quartierService.addQuartierToCollectionIfMissing).toHaveBeenCalledWith(quartierCollection, ...additionalQuartiers);
        expect(comp.quartiersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const lotissement: ILotissement = { id: 456 };
        const quartier: IQuartier = { id: 6065 };
        lotissement.quartier = quartier;

        activatedRoute.data = of({ lotissement });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(lotissement));
        expect(comp.quartiersSharedCollection).toContain(quartier);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const lotissement = { id: 123 };
        spyOn(lotissementService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ lotissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: lotissement }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(lotissementService.update).toHaveBeenCalledWith(lotissement);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const lotissement = new Lotissement();
        spyOn(lotissementService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ lotissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: lotissement }));
        saveSubject.complete();

        // THEN
        expect(lotissementService.create).toHaveBeenCalledWith(lotissement);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const lotissement = { id: 123 };
        spyOn(lotissementService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ lotissement });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(lotissementService.update).toHaveBeenCalledWith(lotissement);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackQuartierById', () => {
        it('Should return tracked Quartier primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackQuartierById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
