import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategorieClotureDetailComponent } from './categorie-cloture-detail.component';

describe('Component Tests', () => {
  describe('CategorieCloture Management Detail Component', () => {
    let comp: CategorieClotureDetailComponent;
    let fixture: ComponentFixture<CategorieClotureDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CategorieClotureDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ categorieCloture: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CategorieClotureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieClotureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorieCloture on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorieCloture).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
