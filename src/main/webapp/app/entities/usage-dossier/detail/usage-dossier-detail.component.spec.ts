import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { UsageDossierDetailComponent } from './usage-dossier-detail.component';

describe('Component Tests', () => {
  describe('UsageDossier Management Detail Component', () => {
    let comp: UsageDossierDetailComponent;
    let fixture: ComponentFixture<UsageDossierDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [UsageDossierDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ usageDossier: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(UsageDossierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UsageDossierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load usageDossier on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.usageDossier).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
