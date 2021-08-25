jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CategorieCoursAmenageService } from '../service/categorie-cours-amenage.service';
import { ICategorieCoursAmenage, CategorieCoursAmenage } from '../categorie-cours-amenage.model';

import { CategorieCoursAmenageUpdateComponent } from './categorie-cours-amenage-update.component';

describe('Component Tests', () => {
  describe('CategorieCoursAmenage Management Update Component', () => {
    let comp: CategorieCoursAmenageUpdateComponent;
    let fixture: ComponentFixture<CategorieCoursAmenageUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let categorieCoursAmenageService: CategorieCoursAmenageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CategorieCoursAmenageUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CategorieCoursAmenageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieCoursAmenageUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      categorieCoursAmenageService = TestBed.inject(CategorieCoursAmenageService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const categorieCoursAmenage: ICategorieCoursAmenage = { id: 456 };

        activatedRoute.data = of({ categorieCoursAmenage });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(categorieCoursAmenage));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieCoursAmenage = { id: 123 };
        spyOn(categorieCoursAmenageService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieCoursAmenage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categorieCoursAmenage }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(categorieCoursAmenageService.update).toHaveBeenCalledWith(categorieCoursAmenage);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieCoursAmenage = new CategorieCoursAmenage();
        spyOn(categorieCoursAmenageService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieCoursAmenage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categorieCoursAmenage }));
        saveSubject.complete();

        // THEN
        expect(categorieCoursAmenageService.create).toHaveBeenCalledWith(categorieCoursAmenage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieCoursAmenage = { id: 123 };
        spyOn(categorieCoursAmenageService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieCoursAmenage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(categorieCoursAmenageService.update).toHaveBeenCalledWith(categorieCoursAmenage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
