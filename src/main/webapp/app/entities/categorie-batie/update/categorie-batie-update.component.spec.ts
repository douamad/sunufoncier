jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CategorieBatieService } from '../service/categorie-batie.service';
import { ICategorieBatie, CategorieBatie } from '../categorie-batie.model';

import { CategorieBatieUpdateComponent } from './categorie-batie-update.component';

describe('Component Tests', () => {
  describe('CategorieBatie Management Update Component', () => {
    let comp: CategorieBatieUpdateComponent;
    let fixture: ComponentFixture<CategorieBatieUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let categorieBatieService: CategorieBatieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CategorieBatieUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CategorieBatieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieBatieUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      categorieBatieService = TestBed.inject(CategorieBatieService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const categorieBatie: ICategorieBatie = { id: 456 };

        activatedRoute.data = of({ categorieBatie });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(categorieBatie));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieBatie = { id: 123 };
        spyOn(categorieBatieService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieBatie });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categorieBatie }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(categorieBatieService.update).toHaveBeenCalledWith(categorieBatie);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieBatie = new CategorieBatie();
        spyOn(categorieBatieService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieBatie });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categorieBatie }));
        saveSubject.complete();

        // THEN
        expect(categorieBatieService.create).toHaveBeenCalledWith(categorieBatie);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieBatie = { id: 123 };
        spyOn(categorieBatieService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieBatie });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(categorieBatieService.update).toHaveBeenCalledWith(categorieBatie);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
