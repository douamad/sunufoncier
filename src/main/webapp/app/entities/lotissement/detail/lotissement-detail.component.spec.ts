import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LotissementDetailComponent } from './lotissement-detail.component';

describe('Component Tests', () => {
  describe('Lotissement Management Detail Component', () => {
    let comp: LotissementDetailComponent;
    let fixture: ComponentFixture<LotissementDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [LotissementDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ lotissement: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(LotissementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LotissementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lotissement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lotissement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
