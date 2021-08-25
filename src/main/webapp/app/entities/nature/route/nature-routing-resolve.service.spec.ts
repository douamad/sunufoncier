jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INature, Nature } from '../nature.model';
import { NatureService } from '../service/nature.service';

import { NatureRoutingResolveService } from './nature-routing-resolve.service';

describe('Service Tests', () => {
  describe('Nature routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: NatureRoutingResolveService;
    let service: NatureService;
    let resultNature: INature | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(NatureRoutingResolveService);
      service = TestBed.inject(NatureService);
      resultNature = undefined;
    });

    describe('resolve', () => {
      it('should return INature returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNature = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNature).toEqual({ id: 123 });
      });

      it('should return new INature if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNature = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultNature).toEqual(new Nature());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultNature = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultNature).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
