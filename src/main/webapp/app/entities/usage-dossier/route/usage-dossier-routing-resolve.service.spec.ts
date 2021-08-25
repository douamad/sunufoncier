jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IUsageDossier, UsageDossier } from '../usage-dossier.model';
import { UsageDossierService } from '../service/usage-dossier.service';

import { UsageDossierRoutingResolveService } from './usage-dossier-routing-resolve.service';

describe('Service Tests', () => {
  describe('UsageDossier routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: UsageDossierRoutingResolveService;
    let service: UsageDossierService;
    let resultUsageDossier: IUsageDossier | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(UsageDossierRoutingResolveService);
      service = TestBed.inject(UsageDossierService);
      resultUsageDossier = undefined;
    });

    describe('resolve', () => {
      it('should return IUsageDossier returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUsageDossier = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultUsageDossier).toEqual({ id: 123 });
      });

      it('should return new IUsageDossier if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUsageDossier = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultUsageDossier).toEqual(new UsageDossier());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultUsageDossier = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultUsageDossier).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
