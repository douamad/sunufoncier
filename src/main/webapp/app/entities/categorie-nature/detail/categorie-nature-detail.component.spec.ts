import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategorieNatureDetailComponent } from './categorie-nature-detail.component';

describe('Component Tests', () => {
  describe('CategorieNature Management Detail Component', () => {
    let comp: CategorieNatureDetailComponent;
    let fixture: ComponentFixture<CategorieNatureDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CategorieNatureDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ categorieNature: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CategorieNatureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieNatureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorieNature on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorieNature).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
