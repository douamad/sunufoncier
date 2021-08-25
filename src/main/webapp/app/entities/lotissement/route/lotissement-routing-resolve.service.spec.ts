jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILotissement, Lotissement } from '../lotissement.model';
import { LotissementService } from '../service/lotissement.service';

import { LotissementRoutingResolveService } from './lotissement-routing-resolve.service';

describe('Service Tests', () => {
  describe('Lotissement routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: LotissementRoutingResolveService;
    let service: LotissementService;
    let resultLotissement: ILotissement | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(LotissementRoutingResolveService);
      service = TestBed.inject(LotissementService);
      resultLotissement = undefined;
    });

    describe('resolve', () => {
      it('should return ILotissement returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLotissement = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLotissement).toEqual({ id: 123 });
      });

      it('should return new ILotissement if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLotissement = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultLotissement).toEqual(new Lotissement());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLotissement = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLotissement).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
