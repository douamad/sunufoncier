jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICategorieCoursAmenage, CategorieCoursAmenage } from '../categorie-cours-amenage.model';
import { CategorieCoursAmenageService } from '../service/categorie-cours-amenage.service';

import { CategorieCoursAmenageRoutingResolveService } from './categorie-cours-amenage-routing-resolve.service';

describe('Service Tests', () => {
  describe('CategorieCoursAmenage routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CategorieCoursAmenageRoutingResolveService;
    let service: CategorieCoursAmenageService;
    let resultCategorieCoursAmenage: ICategorieCoursAmenage | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CategorieCoursAmenageRoutingResolveService);
      service = TestBed.inject(CategorieCoursAmenageService);
      resultCategorieCoursAmenage = undefined;
    });

    describe('resolve', () => {
      it('should return ICategorieCoursAmenage returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieCoursAmenage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCategorieCoursAmenage).toEqual({ id: 123 });
      });

      it('should return new ICategorieCoursAmenage if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieCoursAmenage = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCategorieCoursAmenage).toEqual(new CategorieCoursAmenage());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieCoursAmenage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCategorieCoursAmenage).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
