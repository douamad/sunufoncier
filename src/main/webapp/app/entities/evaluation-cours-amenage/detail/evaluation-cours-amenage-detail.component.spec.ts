import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EvaluationCoursAmenageDetailComponent } from './evaluation-cours-amenage-detail.component';

describe('Component Tests', () => {
  describe('EvaluationCoursAmenage Management Detail Component', () => {
    let comp: EvaluationCoursAmenageDetailComponent;
    let fixture: ComponentFixture<EvaluationCoursAmenageDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [EvaluationCoursAmenageDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ evaluationCoursAmenage: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(EvaluationCoursAmenageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EvaluationCoursAmenageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load evaluationCoursAmenage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.evaluationCoursAmenage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
