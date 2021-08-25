jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { UsageDossierService } from '../service/usage-dossier.service';
import { IUsageDossier, UsageDossier } from '../usage-dossier.model';

import { UsageDossierUpdateComponent } from './usage-dossier-update.component';

describe('Component Tests', () => {
  describe('UsageDossier Management Update Component', () => {
    let comp: UsageDossierUpdateComponent;
    let fixture: ComponentFixture<UsageDossierUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let usageDossierService: UsageDossierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [UsageDossierUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(UsageDossierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UsageDossierUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      usageDossierService = TestBed.inject(UsageDossierService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const usageDossier: IUsageDossier = { id: 456 };

        activatedRoute.data = of({ usageDossier });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(usageDossier));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const usageDossier = { id: 123 };
        spyOn(usageDossierService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ usageDossier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: usageDossier }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(usageDossierService.update).toHaveBeenCalledWith(usageDossier);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const usageDossier = new UsageDossier();
        spyOn(usageDossierService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ usageDossier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: usageDossier }));
        saveSubject.complete();

        // THEN
        expect(usageDossierService.create).toHaveBeenCalledWith(usageDossier);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const usageDossier = { id: 123 };
        spyOn(usageDossierService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ usageDossier });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(usageDossierService.update).toHaveBeenCalledWith(usageDossier);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
