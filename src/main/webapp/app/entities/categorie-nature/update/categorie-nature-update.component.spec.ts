jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CategorieNatureService } from '../service/categorie-nature.service';
import { ICategorieNature, CategorieNature } from '../categorie-nature.model';

import { CategorieNatureUpdateComponent } from './categorie-nature-update.component';

describe('Component Tests', () => {
  describe('CategorieNature Management Update Component', () => {
    let comp: CategorieNatureUpdateComponent;
    let fixture: ComponentFixture<CategorieNatureUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let categorieNatureService: CategorieNatureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CategorieNatureUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CategorieNatureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieNatureUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      categorieNatureService = TestBed.inject(CategorieNatureService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const categorieNature: ICategorieNature = { id: 456 };

        activatedRoute.data = of({ categorieNature });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(categorieNature));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieNature = { id: 123 };
        spyOn(categorieNatureService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieNature });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categorieNature }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(categorieNatureService.update).toHaveBeenCalledWith(categorieNature);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieNature = new CategorieNature();
        spyOn(categorieNatureService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieNature });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categorieNature }));
        saveSubject.complete();

        // THEN
        expect(categorieNatureService.create).toHaveBeenCalledWith(categorieNature);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieNature = { id: 123 };
        spyOn(categorieNatureService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieNature });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(categorieNatureService.update).toHaveBeenCalledWith(categorieNature);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
