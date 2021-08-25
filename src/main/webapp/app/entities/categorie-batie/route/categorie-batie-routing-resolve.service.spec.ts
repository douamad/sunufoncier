jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICategorieBatie, CategorieBatie } from '../categorie-batie.model';
import { CategorieBatieService } from '../service/categorie-batie.service';

import { CategorieBatieRoutingResolveService } from './categorie-batie-routing-resolve.service';

describe('Service Tests', () => {
  describe('CategorieBatie routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CategorieBatieRoutingResolveService;
    let service: CategorieBatieService;
    let resultCategorieBatie: ICategorieBatie | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CategorieBatieRoutingResolveService);
      service = TestBed.inject(CategorieBatieService);
      resultCategorieBatie = undefined;
    });

    describe('resolve', () => {
      it('should return ICategorieBatie returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieBatie = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCategorieBatie).toEqual({ id: 123 });
      });

      it('should return new ICategorieBatie if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieBatie = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCategorieBatie).toEqual(new CategorieBatie());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCategorieBatie = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCategorieBatie).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
