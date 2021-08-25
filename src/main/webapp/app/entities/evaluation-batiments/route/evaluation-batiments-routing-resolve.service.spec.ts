jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEvaluationBatiments, EvaluationBatiments } from '../evaluation-batiments.model';
import { EvaluationBatimentsService } from '../service/evaluation-batiments.service';

import { EvaluationBatimentsRoutingResolveService } from './evaluation-batiments-routing-resolve.service';

describe('Service Tests', () => {
  describe('EvaluationBatiments routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EvaluationBatimentsRoutingResolveService;
    let service: EvaluationBatimentsService;
    let resultEvaluationBatiments: IEvaluationBatiments | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EvaluationBatimentsRoutingResolveService);
      service = TestBed.inject(EvaluationBatimentsService);
      resultEvaluationBatiments = undefined;
    });

    describe('resolve', () => {
      it('should return IEvaluationBatiments returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationBatiments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEvaluationBatiments).toEqual({ id: 123 });
      });

      it('should return new IEvaluationBatiments if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationBatiments = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEvaluationBatiments).toEqual(new EvaluationBatiments());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationBatiments = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEvaluationBatiments).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
