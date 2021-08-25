import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RefParcelaireDetailComponent } from './ref-parcelaire-detail.component';

describe('Component Tests', () => {
  describe('RefParcelaire Management Detail Component', () => {
    let comp: RefParcelaireDetailComponent;
    let fixture: ComponentFixture<RefParcelaireDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RefParcelaireDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ refParcelaire: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RefParcelaireDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RefParcelaireDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load refParcelaire on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.refParcelaire).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
