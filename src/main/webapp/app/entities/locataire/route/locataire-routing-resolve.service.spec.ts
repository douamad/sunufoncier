jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ILocataire, Locataire } from '../locataire.model';
import { LocataireService } from '../service/locataire.service';

import { LocataireRoutingResolveService } from './locataire-routing-resolve.service';

describe('Service Tests', () => {
  describe('Locataire routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: LocataireRoutingResolveService;
    let service: LocataireService;
    let resultLocataire: ILocataire | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(LocataireRoutingResolveService);
      service = TestBed.inject(LocataireService);
      resultLocataire = undefined;
    });

    describe('resolve', () => {
      it('should return ILocataire returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLocataire = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLocataire).toEqual({ id: 123 });
      });

      it('should return new ILocataire if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLocataire = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultLocataire).toEqual(new Locataire());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultLocataire = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultLocataire).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
