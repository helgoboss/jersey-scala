package com.codahale.jersey.inject.specs

import com.codahale.simplespec.Spec
import org.junit.Test
import com.codahale.jersey.inject.ScalaCollectionQueryParamInjectable
import com.sun.jersey.server.impl.model.parameter.multivalued.MultivaluedParameterExtractor
import com.sun.jersey.api.core.{ExtendedUriInfo, HttpContext}
import javax.ws.rs.core.MultivaluedMap

class ScalaCollectionQueryParamInjectableSpec extends Spec {
  // TODO: Aug 17, 2010 <coda> -- test error handling

  val extractor = mock[MultivaluedParameterExtractor]
  val context = mock[HttpContext]
  val uriInfo = mock[ExtendedUriInfo]
  val params = mock[MultivaluedMap[String, String]]
  val extracted = mock[Object]

  extractor.extract(params) returns extracted
  context.getUriInfo returns uriInfo

  class `A Scala collection query param injectable with decoding` {
    val injectable = new ScalaCollectionQueryParamInjectable(extractor, true)
    uriInfo.getQueryParameters(true) returns params

    @Test def `extracts the query parameters` = {
      val e = injectable.getValue(context)

      e.must(be(extracted))
    }
  }

  class `A Scala collection query param injectable without decoding` {
    val injectable = new ScalaCollectionQueryParamInjectable(extractor, false)
    uriInfo.getQueryParameters(false) returns params

    @Test def `extracts the query parameters` = {
      val e = injectable.getValue(context)

      e.must(be(extracted))
    }
  }
}
