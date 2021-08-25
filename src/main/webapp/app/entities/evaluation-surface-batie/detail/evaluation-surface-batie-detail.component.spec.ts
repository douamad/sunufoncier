import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EvaluationSurfaceBatieDetailComponent } from './evaluation-surface-batie-detail.component';

describe('Component Tests', () => {
  describe('EvaluationSurfaceBatie Management Detail Component', () => {
    let comp: EvaluationSurfaceBatieDetailComponent;
    let fixture: ComponentFixture<EvaluationSurfaceBatieDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EvaluationSurfaceBatieDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ evaluationSurfaceBatie: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EvaluationSurfaceBatieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EvaluationSurfaceBatieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load evaluationSurfaceBatie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.evaluationSurfaceBatie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
