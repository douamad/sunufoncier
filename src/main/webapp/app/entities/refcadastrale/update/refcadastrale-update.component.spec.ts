jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RefcadastraleService } from '../service/refcadastrale.service';
import { IRefcadastrale, Refcadastrale } from '../refcadastrale.model';

import { RefcadastraleUpdateComponent } from './refcadastrale-update.component';

describe('Component Tests', () => {
  describe('Refcadastrale Management Update Component', () => {
    let comp: RefcadastraleUpdateComponent;
    let fixture: ComponentFixture<RefcadastraleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let refcadastraleService: RefcadastraleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RefcadastraleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RefcadastraleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RefcadastraleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      refcadastraleService = TestBed.inject(RefcadastraleService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const refcadastrale: IRefcadastrale = { id: 456 };

        activatedRoute.data = of({ refcadastrale });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(refcadastrale));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const refcadastrale = { id: 123 };
        spyOn(refcadastraleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ refcadastrale });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: refcadastrale }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(refcadastraleService.update).toHaveBeenCalledWith(refcadastrale);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const refcadastrale = new Refcadastrale();
        spyOn(refcadastraleService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ refcadastrale });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: refcadastrale }));
        saveSubject.complete();

        // THEN
        expect(refcadastraleService.create).toHaveBeenCalledWith(refcadastrale);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const refcadastrale = { id: 123 };
        spyOn(refcadastraleService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ refcadastrale });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(refcadastraleService.update).toHaveBeenCalledWith(refcadastrale);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
