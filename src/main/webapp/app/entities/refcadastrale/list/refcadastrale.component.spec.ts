import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { RefcadastraleService } from '../service/refcadastrale.service';

import { RefcadastraleComponent } from './refcadastrale.component';

describe('Refcadastrale Management Component', () => {
  let comp: RefcadastraleComponent;
  let fixture: ComponentFixture<RefcadastraleComponent>;
  let service: RefcadastraleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'refcadastrale', component: RefcadastraleComponent }]),
        HttpClientTestingModule,
        RefcadastraleComponent,
      ],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(RefcadastraleComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(RefcadastraleComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(RefcadastraleService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.refcadastrales?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to refcadastraleService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getRefcadastraleIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getRefcadastraleIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
