import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'dossier',
        data: { pageTitle: 'Dossiers' },
        loadChildren: () => import('./dossier/dossier.module').then(m => m.DossierModule),
      },
      {
        path: 'locataire',
        data: { pageTitle: 'Locataires' },
        loadChildren: () => import('./locataire/locataire.module').then(m => m.LocataireModule),
      },
      {
        path: 'evaluation-surface-batie',
        data: { pageTitle: 'EvaluationSurfaceBaties' },
        loadChildren: () => import('./evaluation-surface-batie/evaluation-surface-batie.module').then(m => m.EvaluationSurfaceBatieModule),
      },
      {
        path: 'evaluation-cours-amenage',
        data: { pageTitle: 'EvaluationCoursAmenages' },
        loadChildren: () => import('./evaluation-cours-amenage/evaluation-cours-amenage.module').then(m => m.EvaluationCoursAmenageModule),
      },
      {
        path: 'evaluation-cloture',
        data: { pageTitle: 'EvaluationClotures' },
        loadChildren: () => import('./evaluation-cloture/evaluation-cloture.module').then(m => m.EvaluationClotureModule),
      },
      {
        path: 'evaluation-batiments',
        data: { pageTitle: 'EvaluationBatiments' },
        loadChildren: () => import('./evaluation-batiments/evaluation-batiments.module').then(m => m.EvaluationBatimentsModule),
      },
      {
        path: 'region',
        data: { pageTitle: 'Regions' },
        loadChildren: () => import('./region/region.module').then(m => m.RegionModule),
      },
      {
        path: 'departement',
        data: { pageTitle: 'Departements' },
        loadChildren: () => import('./departement/departement.module').then(m => m.DepartementModule),
      },
      {
        path: 'arrondissement',
        data: { pageTitle: 'Arrondissements' },
        loadChildren: () => import('./arrondissement/arrondissement.module').then(m => m.ArrondissementModule),
      },
      {
        path: 'commune',
        data: { pageTitle: 'Communes' },
        loadChildren: () => import('./commune/commune.module').then(m => m.CommuneModule),
      },
      {
        path: 'quartier',
        data: { pageTitle: 'Quartiers' },
        loadChildren: () => import('./quartier/quartier.module').then(m => m.QuartierModule),
      },
      {
        path: 'lotissement',
        data: { pageTitle: 'Lotissements' },
        loadChildren: () => import('./lotissement/lotissement.module').then(m => m.LotissementModule),
      },
      {
        path: 'nature',
        data: { pageTitle: 'Natures' },
        loadChildren: () => import('./nature/nature.module').then(m => m.NatureModule),
      },
      {
        path: 'usage-dossier',
        data: { pageTitle: 'UsageDossiers' },
        loadChildren: () => import('./usage-dossier/usage-dossier.module').then(m => m.UsageDossierModule),
      },
      {
        path: 'ref-parcelaire',
        data: { pageTitle: 'RefParcelaires' },
        loadChildren: () => import('./ref-parcelaire/ref-parcelaire.module').then(m => m.RefParcelaireModule),
      },
      {
        path: 'refcadastrale',
        data: { pageTitle: 'Refcadastrales' },
        loadChildren: () => import('./refcadastrale/refcadastrale.module').then(m => m.RefcadastraleModule),
      },
      {
        path: 'proprietaire',
        data: { pageTitle: 'Proprietaires' },
        loadChildren: () => import('./proprietaire/proprietaire.module').then(m => m.ProprietaireModule),
      },
      {
        path: 'representant',
        data: { pageTitle: 'Representants' },
        loadChildren: () => import('./representant/representant.module').then(m => m.RepresentantModule),
      },
      {
        path: 'categorie-batie',
        data: { pageTitle: 'CategorieBaties' },
        loadChildren: () => import('./categorie-batie/categorie-batie.module').then(m => m.CategorieBatieModule),
      },
      {
        path: 'categorie-cloture',
        data: { pageTitle: 'CategorieClotures' },
        loadChildren: () => import('./categorie-cloture/categorie-cloture.module').then(m => m.CategorieClotureModule),
      },
      {
        path: 'categorie-cours-amenage',
        data: { pageTitle: 'CategorieCoursAmenages' },
        loadChildren: () => import('./categorie-cours-amenage/categorie-cours-amenage.module').then(m => m.CategorieCoursAmenageModule),
      },
      {
        path: 'categorie-nature',
        data: { pageTitle: 'CategorieNatures' },
        loadChildren: () => import('./categorie-nature/categorie-nature.module').then(m => m.CategorieNatureModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
