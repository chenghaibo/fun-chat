package authentication.entities

sealed trait AuthState

case object Offline extends AuthState

case object Online extends AuthState

case object SigningIn extends AuthState

case object SigningOut extends AuthState

case object Updating extends AuthState
