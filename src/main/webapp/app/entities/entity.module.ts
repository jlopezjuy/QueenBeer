import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { QueenBeerClienteQueenBeerModule } from './cliente-queen-beer/cliente-queen-beer.module';
import { QueenBeerListaPrecioQueenBeerModule } from './lista-precio-queen-beer/lista-precio-queen-beer.module';
import { QueenBeerProductoQueenBeerModule } from './producto-queen-beer/producto-queen-beer.module';
import { QueenBeerProveedorQueenBeerModule } from './proveedor-queen-beer/proveedor-queen-beer.module';
import { QueenBeerInsumoQueenBeerModule } from './insumo-queen-beer/insumo-queen-beer.module';
import { QueenBeerElaboracionQueenBeerModule } from './elaboracion-queen-beer/elaboracion-queen-beer.module';
import { QueenBeerElaboracionInsumoQueenBeerModule } from './elaboracion-insumo-queen-beer/elaboracion-insumo-queen-beer.module';
import { QueenBeerCompraQueenBeerModule } from './compra-queen-beer/compra-queen-beer.module';
import { QueenBeerCompraInsumoQueenBeerModule } from './compra-insumo-queen-beer/compra-insumo-queen-beer.module';
import { QueenBeerUserProfileModule } from './user-profile/user-profile.module';
import { QueenBeerEnvaseModule } from './envase/envase.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        QueenBeerClienteQueenBeerModule,
        QueenBeerListaPrecioQueenBeerModule,
        QueenBeerProductoQueenBeerModule,
        QueenBeerProveedorQueenBeerModule,
        QueenBeerInsumoQueenBeerModule,
        QueenBeerElaboracionQueenBeerModule,
        QueenBeerElaboracionInsumoQueenBeerModule,
        QueenBeerCompraQueenBeerModule,
        QueenBeerCompraInsumoQueenBeerModule,
        QueenBeerUserProfileModule,
        QueenBeerEnvaseModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QueenBeerEntityModule {}
