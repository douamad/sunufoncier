import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { LocataireDetailComponent } from './locataire-detail.component';

describe('Component Tests', () => {
  describe('Locataire Management Detail Component', () => {
    let comp: LocataireDetailComponent;
    let fixture: ComponentFixture<LocataireDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [LocataireDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ locataire: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(LocataireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LocataireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load locataire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.locataire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
