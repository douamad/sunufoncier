jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CategorieClotureService } from '../service/categorie-cloture.service';
import { ICategorieCloture, CategorieCloture } from '../categorie-cloture.model';

import { CategorieClotureUpdateComponent } from './categorie-cloture-update.component';

describe('Component Tests', () => {
  describe('CategorieCloture Management Update Component', () => {
    let comp: CategorieClotureUpdateComponent;
    let fixture: ComponentFixture<CategorieClotureUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let categorieClotureService: CategorieClotureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CategorieClotureUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CategorieClotureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorieClotureUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      categorieClotureService = TestBed.inject(CategorieClotureService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const categorieCloture: ICategorieCloture = { id: 456 };

        activatedRoute.data = of({ categorieCloture });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(categorieCloture));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieCloture = { id: 123 };
        spyOn(categorieClotureService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieCloture });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categorieCloture }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(categorieClotureService.update).toHaveBeenCalledWith(categorieCloture);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieCloture = new CategorieCloture();
        spyOn(categorieClotureService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieCloture });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: categorieCloture }));
        saveSubject.complete();

        // THEN
        expect(categorieClotureService.create).toHaveBeenCalledWith(categorieCloture);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const categorieCloture = { id: 123 };
        spyOn(categorieClotureService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ categorieCloture });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(categorieClotureService.update).toHaveBeenCalledWith(categorieCloture);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
