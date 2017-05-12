package longevity.unit.manual

package ModelTypeSpec {

  // used in http://longevityframework.github.io/longevity/manual/model/persistents.html
  package persistents1 {
    import longevity.model.annotations.persistent

    @persistent(keySet = emptyKeySet)
    case class User(
      username: String,
      firstName: String,
      lastName: String)
  }

  // used in http://longevityframework.github.io/longevity/manual/model/persistents.html
  package persistents2 {
    import longevity.model.PType

    case class User(
      username: String,
      firstName: String,
      lastName: String)

    object User extends PType[User] {
      object props {
      }
      val keySet = emptyKeySet
    }
  }

  // used in http://longevityframework.github.io/longevity/manual/model/basics.html
  package basics {
    import longevity.model.annotations.persistent
    import org.joda.time.DateTime

    @persistent(keySet = emptyKeySet)
    case class User(
      username: String,
      firstName: String,
      lastName: String,
      dateJoined: DateTime,
      numCats: Int,
      isSuspended: Boolean = false)   
  }

  // used in http://longevityframework.github.io/longevity/manual/model/collections.html
  package collections {
    import longevity.model.annotations.persistent

    @persistent(keySet = emptyKeySet)
    case class User(
      username: String,
      title: Option[String],
      firstName: String,
      lastName: String,
      emails: Set[String])   
  }

  // used in http://longevityframework.github.io/longevity/manual/model/components.html
  package components1 {
    import longevity.model.annotations.component
    import longevity.model.annotations.persistent

    @component
    case class FullName(
      firstName: String,
      lastName: String)   

    @persistent(keySet = emptyKeySet)
    case class User(
      username: String,
      fullName: FullName)   
  }

  // used in http://longevityframework.github.io/longevity/manual/components/index.html
  package components2 {
    import longevity.model.CType

    case class FullName(
      firstName: String,
      lastName: String)

    object FullName extends CType[FullName]
  }

  // used in http://longevityframework.github.io/longevity/manual/components/index.html
  package components3 {
    import longevity.model.annotations.component
    import longevity.model.annotations.persistent

    @component
    case class Email(email: String)

    @component
    case class EmailPreferences(
      primaryEmail: Email,
      emails: Set[Email])

    @component
    case class Address(
      street: String,
      city: String)

    @persistent(keySet = emptyKeySet)
    case class User(
      username: String,
      emails: EmailPreferences,
      addresses: Set[Address])
  }

  // used in http://longevityframework.github.io/longevity/manual/model/key-values.html
  package keyValues1 {
    import longevity.model.annotations.keyVal
    import longevity.model.annotations.persistent

    @keyVal[User]
    case class Username(username: String)

    @persistent(keySet = Set(key(User.props.username)))
    case class User(
      username: Username,
      firstName: String,
      lastName: String)
  }

  // used in http://longevityframework.github.io/longevity/manual/model/key-values.html
  package keyValues2 {
    import longevity.model.annotations.keyVal
    import longevity.model.annotations.persistent

    @keyVal[User]
    case class Username(username: String)

    @persistent(keySet = Set(key(User.props.username)))
    case class User(
      username: Username,
      firstName: String,
      lastName: String,
      sponsor: Option[Username])
  }

  // used in http://longevityframework.github.io/longevity/manual/model/key-values.html
  package keyValues3 {
    import longevity.model.KeyVal

    case class Username(username: String) extends KeyVal[User]

    case class User(
      username: Username,
      firstName: String,
      lastName: String,
      sponsor: Option[Username])
  }

  // used in http://longevityframework.github.io/longevity/manual/model/domainModel.html
  package domainModel1 {
    import longevity.model.annotations.domainModel

    @domainModel trait MyDomainModel
  }

  // used in http://longevityframework.github.io/longevity/manual/model/domainModel.html
  package domainModel2 {
    import longevity.model.ModelType

    trait MyDomainModel

    object MyDomainModel {
      implicit object myModelType extends ModelType[MyDomainModel]("myPackage")
    }
  }

  // used in http://longevityframework.github.io/longevity/manual/model/domainModel.html
  package domainModel3 {
    import longevity.model.annotations.persistent
    import longevity.model.annotations.component

    @persistent(keySet = emptyKeySet) case class User()
    @persistent(keySet = emptyKeySet) case class BlogPost()
    @persistent(keySet = emptyKeySet) case class Blog()
    @component case class UserProfile()

    import longevity.model.ModelType
    import longevity.model.CTypePool
    import longevity.model.PTypePool

    trait MyDomainModel

    object MyDomainModel {
      implicit object myModelType extends ModelType[MyDomainModel](
        PTypePool(User, BlogPost, Blog),
        CTypePool(UserProfile))
    }
  }

}
