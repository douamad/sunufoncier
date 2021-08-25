jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEvaluationCloture, EvaluationCloture } from '../evaluation-cloture.model';
import { EvaluationClotureService } from '../service/evaluation-cloture.service';

import { EvaluationClotureRoutingResolveService } from './evaluation-cloture-routing-resolve.service';

describe('Service Tests', () => {
  describe('EvaluationCloture routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EvaluationClotureRoutingResolveService;
    let service: EvaluationClotureService;
    let resultEvaluationCloture: IEvaluationCloture | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EvaluationClotureRoutingResolveService);
      service = TestBed.inject(EvaluationClotureService);
      resultEvaluationCloture = undefined;
    });

    describe('resolve', () => {
      it('should return IEvaluationCloture returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationCloture = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEvaluationCloture).toEqual({ id: 123 });
      });

      it('should return new IEvaluationCloture if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationCloture = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEvaluationCloture).toEqual(new EvaluationCloture());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEvaluationCloture = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultEvaluationCloture).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
