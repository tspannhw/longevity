package longevity.integration.model.complexConstraint

import org.scalatest.Suites

class ComplexConstraintSpec extends Suites(contexts.map(_.repoCrudSpec): _*)
