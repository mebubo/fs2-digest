package playground

import cats.effect.IO
import cats.effect.IOApp
import cats.effect.Blocker
import cats.effect.ExitCode
import java.nio.file.Paths

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val path = Paths.get(args(0))
    Blocker[IO].use(App.app[IO](path))
  }

}
