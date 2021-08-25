jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRefcadastrale, Refcadastrale } from '../refcadastrale.model';
import { RefcadastraleService } from '../service/refcadastrale.service';

import { RefcadastraleRoutingResolveService } from './refcadastrale-routing-resolve.service';

describe('Service Tests', () => {
  describe('Refcadastrale routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RefcadastraleRoutingResolveService;
    let service: RefcadastraleService;
    let resultRefcadastrale: IRefcadastrale | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RefcadastraleRoutingResolveService);
      service = TestBed.inject(RefcadastraleService);
      resultRefcadastrale = undefined;
    });

    describe('resolve', () => {
      it('should return IRefcadastrale returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRefcadastrale = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRefcadastrale).toEqual({ id: 123 });
      });

      it('should return new IRefcadastrale if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRefcadastrale = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRefcadastrale).toEqual(new Refcadastrale());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRefcadastrale = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRefcadastrale).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
