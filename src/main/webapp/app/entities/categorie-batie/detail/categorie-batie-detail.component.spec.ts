import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CategorieBatieDetailComponent } from './categorie-batie-detail.component';

describe('Component Tests', () => {
  describe('CategorieBatie Management Detail Component', () => {
    let comp: CategorieBatieDetailComponent;
    let fixture: ComponentFixture<CategorieBatieDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CategorieBatieDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ categorieBatie: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CategorieBatieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorieBatieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load categorieBatie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorieBatie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
