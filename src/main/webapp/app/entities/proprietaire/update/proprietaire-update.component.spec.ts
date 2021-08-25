jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ProprietaireService } from '../service/proprietaire.service';
import { IProprietaire, Proprietaire } from '../proprietaire.model';

import { ProprietaireUpdateComponent } from './proprietaire-update.component';

describe('Component Tests', () => {
  describe('Proprietaire Management Update Component', () => {
    let comp: ProprietaireUpdateComponent;
    let fixture: ComponentFixture<ProprietaireUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let proprietaireService: ProprietaireService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProprietaireUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ProprietaireUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProprietaireUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      proprietaireService = TestBed.inject(ProprietaireService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const proprietaire: IProprietaire = { id: 456 };

        activatedRoute.data = of({ proprietaire });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(proprietaire));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const proprietaire = { id: 123 };
        spyOn(proprietaireService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ proprietaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: proprietaire }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(proprietaireService.update).toHaveBeenCalledWith(proprietaire);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const proprietaire = new Proprietaire();
        spyOn(proprietaireService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ proprietaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: proprietaire }));
        saveSubject.complete();

        // THEN
        expect(proprietaireService.create).toHaveBeenCalledWith(proprietaire);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const proprietaire = { id: 123 };
        spyOn(proprietaireService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ proprietaire });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(proprietaireService.update).toHaveBeenCalledWith(proprietaire);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
