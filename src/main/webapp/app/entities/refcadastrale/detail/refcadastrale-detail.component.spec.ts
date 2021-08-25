import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RefcadastraleDetailComponent } from './refcadastrale-detail.component';

describe('Component Tests', () => {
  describe('Refcadastrale Management Detail Component', () => {
    let comp: RefcadastraleDetailComponent;
    let fixture: ComponentFixture<RefcadastraleDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [RefcadastraleDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ refcadastrale: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(RefcadastraleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RefcadastraleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load refcadastrale on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.refcadastrale).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
