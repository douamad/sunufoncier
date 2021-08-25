import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EvaluationClotureDetailComponent } from './evaluation-cloture-detail.component';

describe('Component Tests', () => {
  describe('EvaluationCloture Management Detail Component', () => {
    let comp: EvaluationClotureDetailComponent;
    let fixture: ComponentFixture<EvaluationClotureDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EvaluationClotureDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ evaluationCloture: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EvaluationClotureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EvaluationClotureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load evaluationCloture on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.evaluationCloture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
