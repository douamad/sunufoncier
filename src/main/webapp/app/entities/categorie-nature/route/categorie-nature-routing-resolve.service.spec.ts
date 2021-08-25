jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICategorieNature, CategorieNature } from '../categorie-nature.model';
import { CategorieNatureService } from '../service/categorie-nature.service';

import { CategorieNatureRoutingResolveService } from './categorie-nature-routing-resolve.service';

describe('Service Tests', () => {
  describe('CategorieNature routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CategorieNatureRoutingResolveService;
    let service: CategorieNatureService;
    let resultCategorieNature: ICategorieNature | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CategorieNatureRoutingResolveService);
      service = TestBed.inject(CategorieNatureService);
      resultCategorieNature = undefined;
    });

    describe('resolve', () => {
      it('should return ICategorieNature returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieNature = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCategorieNature).toEqual({ id: 123 });
      });

      it('should return new ICategorieNature if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieNature = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCategorieNature).toEqual(new CategorieNature());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieNature = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCategorieNature).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
