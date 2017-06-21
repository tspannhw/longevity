
lazy val root = Project(id = "root", base = file("."), settings = BuildSettings.noPublishSettings)
  .aggregate(bin, longevity, longevityCassandraDeps, longevityMongoDbDeps, longevitySqliteDeps)

lazy val bin = Project(id = "bin", base = file("bin"), settings = BuildSettings.noPublishSettings)

lazy val longevity = project.in(file("longevity"))
  .settings(BuildSettings.buildSettings: _*)
  .settings(
    libraryDependencies += Dependencies.acyclicDep         % Provided,
    libraryDependencies += Dependencies.akkaStreamDep      % Optional,
    libraryDependencies += Dependencies.akkaStreamDep      % Test,
    libraryDependencies += Dependencies.cassandraDep       % Optional,
    libraryDependencies += Dependencies.cassandraDep       % Test,
    libraryDependencies += Dependencies.catsDep            % Optional,
    libraryDependencies += Dependencies.catsDep            % Test,
    libraryDependencies += Dependencies.catsIterateeDep    % Optional,
    libraryDependencies += Dependencies.catsIterateeDep    % Test,
    libraryDependencies += Dependencies.fs2CoreDep         % Optional,
    libraryDependencies += Dependencies.fs2CoreDep         % Test,
    libraryDependencies += Dependencies.json4sDep          % Optional,
    libraryDependencies += Dependencies.json4sDep          % Test,
    libraryDependencies += Dependencies.kxbmapConfigsDep,
    libraryDependencies += Dependencies.mongodbDep         % Optional,
    libraryDependencies += Dependencies.mongodbDep         % Test,
    libraryDependencies += Dependencies.playIterateeDep    % Optional,
    libraryDependencies += Dependencies.playIterateeDep    % Test,
    libraryDependencies += Dependencies.scalaLoggingDep,
    libraryDependencies += Dependencies.scalaTestDep       % Optional,
    libraryDependencies += Dependencies.slf4jSimpleDep     % Test,
    libraryDependencies += Dependencies.sqliteDep          % Optional,
    libraryDependencies += Dependencies.sqliteDep          % Test,
    libraryDependencies += Dependencies.streamAdapterDep,
    libraryDependencies += Dependencies.typesafeConfigDep,
    libraryDependencies += Dependencies.typekeyDep,

    homepage := BuildSettings.longevityHomepage,
    pomExtra := BuildSettings.longevityPomExtra,

    addCompilerPlugin("com.lihaoyi"    %% "acyclic"        % "0.1.7"),
    addCompilerPlugin("org.scalamacros" % "paradise"       % "2.1.0" cross CrossVersion.full),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4"))
  .enablePlugins(JekyllPlugin, SiteScaladocPlugin, GhpagesPlugin)
  .settings(
    git.remoteRepo := "git@github.com:longevityframework/longevity.git",
    includeFilter in Jekyll :=
      ("*.html" | "*.png" | "*.js" | "*.css" | "*.gif" | "CNAME" | ".nojekyll" | "*.json" | "*.jpg"),
    siteSubdirName in SiteScaladoc := "api")

lazy val longevityCassandraDeps = Project(
  id = "longevity-cassandra-deps",
  base = file("longevity-cassandra-deps"),
  settings = BuildSettings.publishSettings ++ Seq(
    libraryDependencies += Dependencies.cassandraDep,
    libraryDependencies += Dependencies.json4sDep,
    homepage := BuildSettings.longevityHomepage,
    pomExtra := BuildSettings.longevityPomExtra))

lazy val longevityMongoDbDeps = Project(
  id = "longevity-mongodb-deps",
  base = file("longevity-mongodb-deps"),
  settings = BuildSettings.publishSettings ++ Seq(
    libraryDependencies += Dependencies.mongodbDep,
    homepage := BuildSettings.longevityHomepage,
    pomExtra := BuildSettings.longevityPomExtra))

lazy val longevitySqliteDeps = Project(
  id = "longevity-sqlite-deps",
  base = file("longevity-sqlite-deps"),
  settings = BuildSettings.publishSettings ++ Seq(
    libraryDependencies += Dependencies.sqliteDep,
    homepage := BuildSettings.longevityHomepage,
    pomExtra := BuildSettings.longevityPomExtra))
