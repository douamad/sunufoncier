import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategorieCoursAmenageDetailComponent } from './categorie-cours-amenage-detail.component';

describe('Component Tests', () => {
  describe('CategorieCoursAmenage Management Detail Component', () => {
    let comp: CategorieCoursAmenageDetailComponent;
    let fixture: ComponentFixture<CategorieCoursAmenageDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CategorieCoursAmenageDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ categorieCoursAmenage: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CategorieCoursAmenageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieCoursAmenageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorieCoursAmenage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorieCoursAmenage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
