import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NatureDetailComponent } from './nature-detail.component';

describe('Component Tests', () => {
  describe('Nature Management Detail Component', () => {
    let comp: NatureDetailComponent;
    let fixture: ComponentFixture<NatureDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NatureDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ nature: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NatureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NatureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nature on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nature).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
