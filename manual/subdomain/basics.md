---
title: basic properties
layout: page
---

In the previous example, we saw how to build a `RootEntity` with a few
`String` properties. Of course, we can build aggregates with a lot of
other kinds of properties than that. The simplest kinds are ["basic
properties"](http://sullivan-.github.io/longevity/scaladocs/emblem-latest/#emblem.basicTypes$),
allowing you to put in properties with a number of simple types:

- `java.lang.String`
- `org.joda.time.DateTime`
- `scala.Boolean`
- `scala.Char`
- `scala.Double`
- `scala.Float`
- `scala.Int`
- `scala.Long`

For example, we might add a few fields to our `User` like so:

{% gist sullivan-/58f8ae308d9ca96dbd63 %}

{% capture content %}

We recommend using <a href =
"https://github.com/nscala-time/nscala-time">nscala-time</a> to wrap
your <a href = "http://www.joda.org/joda-time/">Joda-Time</a> dates in
a Scala-friendly wrapper.

{% endcapture %}
{% include longevity-meta.html title = "nscala-time" content = content %}

{% assign prevTitle = "natural keys" %}
{% assign prevLink = "keys.html" %}
{% assign upTitle = "building your subdomain" %}
{% assign upLink = "." %}
{% assign nextTitle="collections" %}
{% assign nextLink="collections.html" %}
{% include navigate.html %}
