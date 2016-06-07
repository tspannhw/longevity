package longevity.integration.createMany

import longevity.integration.subdomain.withAssoc

/** unit tests for the [[RepoPool.createMany]] method against Mongo back end */
class MongoCreateManySpec extends BaseCreateManySpec(
  withAssoc.mongoContext,
  withAssoc.mongoContext.testRepoPool)