import com.typesafe.scalalogging.StrictLogging

object Main extends App with StrictLogging {

  try {
    val funChatBootstrapping = new Bootstrap()
    funChatBootstrapping.startup()
  } catch {
    case ex: Exception => logger.error("Unexpected error occurred!", ex)
  }
}
