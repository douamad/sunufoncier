jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEvaluationCoursAmenage, EvaluationCoursAmenage } from '../evaluation-cours-amenage.model';
import { EvaluationCoursAmenageService } from '../service/evaluation-cours-amenage.service';

import { EvaluationCoursAmenageRoutingResolveService } from './evaluation-cours-amenage-routing-resolve.service';

describe('Service Tests', () => {
  describe('EvaluationCoursAmenage routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EvaluationCoursAmenageRoutingResolveService;
    let service: EvaluationCoursAmenageService;
    let resultEvaluationCoursAmenage: IEvaluationCoursAmenage | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EvaluationCoursAmenageRoutingResolveService);
      service = TestBed.inject(EvaluationCoursAmenageService);
      resultEvaluationCoursAmenage = undefined;
    });

    describe('resolve', () => {
      it('should return IEvaluationCoursAmenage returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationCoursAmenage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEvaluationCoursAmenage).toEqual({ id: 123 });
      });

      it('should return new IEvaluationCoursAmenage if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationCoursAmenage = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEvaluationCoursAmenage).toEqual(new EvaluationCoursAmenage());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationCoursAmenage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEvaluationCoursAmenage).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
