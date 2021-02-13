package rokinsky.hello

object Hello {
  def helloMethod(name: String): String = s"Hello, ${name}!"

  val helloFunction: String => String = (name: String) => s"Hello, ${name}!"
}
