import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { RefcadastraleFormService } from './refcadastrale-form.service';
import { RefcadastraleService } from '../service/refcadastrale.service';
import { IRefcadastrale } from '../refcadastrale.model';

import { RefcadastraleUpdateComponent } from './refcadastrale-update.component';

describe('Refcadastrale Management Update Component', () => {
  let comp: RefcadastraleUpdateComponent;
  let fixture: ComponentFixture<RefcadastraleUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let refcadastraleFormService: RefcadastraleFormService;
  let refcadastraleService: RefcadastraleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), RefcadastraleUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(RefcadastraleUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RefcadastraleUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    refcadastraleFormService = TestBed.inject(RefcadastraleFormService);
    refcadastraleService = TestBed.inject(RefcadastraleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const refcadastrale: IRefcadastrale = { id: 456 };

      activatedRoute.data = of({ refcadastrale });
      comp.ngOnInit();

      expect(comp.refcadastrale).toEqual(refcadastrale);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRefcadastrale>>();
      const refcadastrale = { id: 123 };
      jest.spyOn(refcadastraleFormService, 'getRefcadastrale').mockReturnValue(refcadastrale);
      jest.spyOn(refcadastraleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refcadastrale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: refcadastrale }));
      saveSubject.complete();

      // THEN
      expect(refcadastraleFormService.getRefcadastrale).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(refcadastraleService.update).toHaveBeenCalledWith(expect.objectContaining(refcadastrale));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRefcadastrale>>();
      const refcadastrale = { id: 123 };
      jest.spyOn(refcadastraleFormService, 'getRefcadastrale').mockReturnValue({ id: null });
      jest.spyOn(refcadastraleService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refcadastrale: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: refcadastrale }));
      saveSubject.complete();

      // THEN
      expect(refcadastraleFormService.getRefcadastrale).toHaveBeenCalled();
      expect(refcadastraleService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IRefcadastrale>>();
      const refcadastrale = { id: 123 };
      jest.spyOn(refcadastraleService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ refcadastrale });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(refcadastraleService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
