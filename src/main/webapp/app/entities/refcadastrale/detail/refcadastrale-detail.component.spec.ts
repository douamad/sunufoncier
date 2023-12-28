import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RefcadastraleDetailComponent } from './refcadastrale-detail.component';

describe('Refcadastrale Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RefcadastraleDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: RefcadastraleDetailComponent,
              resolve: { refcadastrale: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding()
        ),
      ],
    })
      .overrideTemplate(RefcadastraleDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load refcadastrale on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', RefcadastraleDetailComponent);

      // THEN
      expect(instance.refcadastrale).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
