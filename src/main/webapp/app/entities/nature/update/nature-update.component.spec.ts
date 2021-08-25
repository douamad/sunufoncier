jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { NatureService } from '../service/nature.service';
import { INature, Nature } from '../nature.model';

import { NatureUpdateComponent } from './nature-update.component';

describe('Component Tests', () => {
  describe('Nature Management Update Component', () => {
    let comp: NatureUpdateComponent;
    let fixture: ComponentFixture<NatureUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let natureService: NatureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NatureUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(NatureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NatureUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      natureService = TestBed.inject(NatureService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const nature: INature = { id: 456 };

        activatedRoute.data = of({ nature });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(nature));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nature = { id: 123 };
        spyOn(natureService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nature });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nature }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(natureService.update).toHaveBeenCalledWith(nature);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nature = new Nature();
        spyOn(natureService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nature });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: nature }));
        saveSubject.complete();

        // THEN
        expect(natureService.create).toHaveBeenCalledWith(nature);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const nature = { id: 123 };
        spyOn(natureService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ nature });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(natureService.update).toHaveBeenCalledWith(nature);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
