import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EvaluationBatimentsDetailComponent } from './evaluation-batiments-detail.component';

describe('Component Tests', () => {
  describe('EvaluationBatiments Management Detail Component', () => {
    let comp: EvaluationBatimentsDetailComponent;
    let fixture: ComponentFixture<EvaluationBatimentsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EvaluationBatimentsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ evaluationBatiments: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EvaluationBatimentsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EvaluationBatimentsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load evaluationBatiments on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.evaluationBatiments).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
