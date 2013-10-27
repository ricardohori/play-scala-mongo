import br.com.sofist.modules.DevModule
import com.google.inject.Guice
import play.api.GlobalSettings

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/27/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
object Global extends GlobalSettings{
    val injector = Guice.createInjector(new DevModule)

    override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)
}
