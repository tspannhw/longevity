package longevity.unit.manual

import org.scalatest.FlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.Matchers
import org.scalatest.OptionValues.convertOptionToValuable

/** code samples found in the persistent types section of the user manual
 *
 * @see http://longevityframework.github.io/longevity/manual/ptype
 */
object PTypeSpec {

  // used in http://longevityframework.github.io/longevity/manual/root-type/properties.html
  object properties1 {

    import longevity.subdomain.Shorthand
    import longevity.subdomain.ShorthandPool

    case class Email(email: String)
    case class Markdown(markdown: String)
    case class Uri(uri: String)
    val emailShorthand = Shorthand[Email, String]
    val markdownShorthand = Shorthand[Markdown, String]
    val uriShorthand = Shorthand[Uri, String]
    implicit val shorthandPool = ShorthandPool(emailShorthand, markdownShorthand, uriShorthand)

    import longevity.subdomain.Entity
    import longevity.subdomain.EntityType
    import longevity.subdomain.EntityTypePool
    import longevity.subdomain.Subdomain
    import longevity.subdomain.persistent.Root
    import longevity.subdomain.ptype.RootType

    case class UserProfile(
      tagline: String,
      imageUri: Uri,
      description: Markdown)
    extends Entity

    object UserProfile extends EntityType[UserProfile]

    case class User(
      username: String,
      email: Email,
      profile: UserProfile)
    extends Root

    object User extends RootType[User] {

      // fully typed:
      val profileDescription: longevity.subdomain.ptype.Prop[User, Markdown] =
        prop[Markdown]("profile.description")

      // brief:
      val usernameProp = prop[String]("username")

      object keys {
      }
      object indexes {
      }
    }

    val subdomain = Subdomain("blogging", EntityTypePool(User, UserProfile))
  }

  // used in http://longevityframework.github.io/longevity/manual/root-type/properties.html
  object properties2 {

    import longevity.subdomain.EntityTypePool
    import longevity.subdomain.Subdomain
    import longevity.subdomain.persistent.Root
    import longevity.subdomain.ptype.RootType

    case class User(
      username: String,
      firstName: String,
      lastName: String,
      email: String)
    extends Root

    object User extends RootType[User] {
      object props {
        val username = prop[String]("username")
        val firstName = prop[String]("firstName")
        val lastName = prop[String]("lastName")
        val email = prop[String]("email")
      }
      object keys {
      }
      object indexes {
      }
    }

    val subdomain = Subdomain("blogging", EntityTypePool(User))
  }

  // used in http://longevityframework.github.io/longevity/manual/root-type/keys.html
  object keys1 {

    import longevity.subdomain.EntityTypePool
    import longevity.subdomain.Subdomain
    import longevity.subdomain.persistent.Root
    import longevity.subdomain.ptype.RootType

    case class User(
      username: String,
      firstName: String,
      lastName: String)
    extends Root

    object User extends RootType[User] {
      object props {
        val username = prop[String]("username")
        val firstName = prop[String]("firstName")
        val lastName = prop[String]("lastName")
      }
      object keys {
        val username = key(props.username)  
      }
      object indexes {
      }
    }

    val subdomain = Subdomain("blogging", EntityTypePool(User))
  }

  // used in http://longevityframework.github.io/longevity/manual/root-type/keys.html
  object keys2 {

    import longevity.subdomain.EntityTypePool
    import longevity.subdomain.Subdomain
    import longevity.subdomain.persistent.Root
    import longevity.subdomain.ptype.RootType

    case class User(
      username: String,
      firstName: String,
      lastName: String)
    extends Root

    object User extends RootType[User] {
      object props {
        val username = prop[String]("username")
        val firstName = prop[String]("firstName")
        val lastName = prop[String]("lastName")
      }
      object keys {
        val username = key(props.username)
        val fullname = key(props.firstName, props.lastName)
      }
      object indexes {
      }
    }

    val subdomain = Subdomain("blogging", EntityTypePool(User))
  }

  // used in http://longevityframework.github.io/longevity/manual/root-type/indexes.html
  object indexes1 {

    import longevity.subdomain.EntityTypePool
    import longevity.subdomain.Subdomain
    import longevity.subdomain.persistent.Root
    import longevity.subdomain.ptype.RootType

    case class User(
      username: String,
      firstName: String,
      lastName: String)
    extends Root

    object User extends RootType[User] {
      object props {
        val username = prop[String]("username")
        val firstName = prop[String]("firstName")
        val lastName = prop[String]("lastName")
      }
      object keys {
        val username = key(props.username)
      }
      object indexes {
        val fullname = index(props.lastName, props.firstName)
      }
    }

    val subdomain = Subdomain("blogging", EntityTypePool(User))
  }

  // used in http://longevityframework.github.io/longevity/manual/root-type/key-sets-and-index-sets.html
  object sets {

    import longevity.subdomain.persistent.Root
    import longevity.subdomain.ptype.Index
    import longevity.subdomain.ptype.Key
    import longevity.subdomain.ptype.RootType

    case class User(
      username: String,
      firstName: String,
      lastName: String)
    extends Root

    object User extends RootType[User] {
      override lazy val keySet = Set.empty[Key[User]]
      override lazy val indexSet = Set.empty[Index[User]]
    }

    import longevity.subdomain.EntityTypePool
    import longevity.subdomain.Subdomain

    val subdomain = Subdomain("blogging", EntityTypePool(User))
  }

}

/** exercises code samples found in the root type section of the user manual.
 * the samples themselves are in [[PTypeSpec]] companion object. we include
 * them in the tests here to force the initialization of the subdomains, and to
 * perform some basic sanity checks on the results.
 *
 * @see http://longevityframework.github.io/longevity/manual/root-type
 */
class PTypeSpec extends FlatSpec with GivenWhenThen with Matchers {

  import PTypeSpec._
  import emblem.typeKey

  "user manual example code" should "produce correct subdomains" in {

    properties1.subdomain.name should equal ("blogging")
    properties2.subdomain.name should equal ("blogging")

    {
      keys1.subdomain.name should equal ("blogging")
      keys1.subdomain.entityTypePool.size should equal (1)
      keys1.subdomain.entityTypePool.values.head should equal (keys1.User)
      keys1.subdomain.pTypePool.size should equal (1)
      keys1.subdomain.pTypePool.values.head should equal (keys1.User)
      keys1.User.keySet.size should equal (1)
      keys1.User.keySet.head should equal (keys1.User.keys.username)
      keys1.User.keys.username.props.size should equal (1)
      val prop = keys1.User.keys.username.props.head
      prop.path should equal ("username")
      prop.propTypeKey should equal (typeKey[String])
    }

    {
      keys2.subdomain.name should equal ("blogging")
      keys2.subdomain.entityTypePool.size should equal (1)
      keys2.subdomain.entityTypePool.values.head should equal (keys2.User)
      keys2.subdomain.pTypePool.size should equal (1)
      keys2.subdomain.pTypePool.values.head should equal (keys2.User)
      keys2.User.keySet.size should equal (2)
      keys2.User.keySet.find(_.props.size == 1).value should equal (keys2.User.keys.username)
      keys2.User.keys.username.props.size should equal (1)
      val usernameProp = keys2.User.keys.username.props.head
      usernameProp.path should equal ("username")
      usernameProp.propTypeKey should equal (typeKey[String])

      keys2.User.keySet.find(_.props.size == 2).value should equal (keys2.User.keys.fullname)
      keys2.User.keys.fullname.props.size should equal (2)
      val firstNameProp = keys2.User.keys.fullname.props.find(_.path == "firstName").value
      firstNameProp.path should equal ("firstName")
      firstNameProp.propTypeKey should equal (typeKey[String])
      val lastNameProp = keys2.User.keys.fullname.props.find(_.path == "lastName").value
      lastNameProp.path should equal ("lastName")
      lastNameProp.propTypeKey should equal (typeKey[String])
    }

    indexes1.subdomain.name should equal ("blogging")
    sets.subdomain.name should equal ("blogging")

  }

}